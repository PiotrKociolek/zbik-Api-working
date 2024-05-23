package pl.pkociolek.zbik.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.pkociolek.zbik.components.PasswordEncoder;
import pl.pkociolek.zbik.exception.*;
import pl.pkociolek.zbik.model.Role;
import pl.pkociolek.zbik.model.TokenType;
import pl.pkociolek.zbik.model.dtos.mail.MailRequest;
import pl.pkociolek.zbik.model.dtos.request.AdminRequestDto;
import pl.pkociolek.zbik.model.dtos.user.*;
import pl.pkociolek.zbik.repository.TokenRepository;
import pl.pkociolek.zbik.repository.UserRepository;
import pl.pkociolek.zbik.repository.entity.TokenEntity;
import pl.pkociolek.zbik.repository.entity.UserEntity;
import pl.pkociolek.zbik.service.UserService;
import pl.pkociolek.zbik.utilities.EmailTemplate;
import pl.pkociolek.zbik.utilities.jwt.JwtTokenEncoder;

@Service
//@Transactional
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

  // Pola serwisu
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenEncoder tokenEncoder;
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;


  private final Path root = Paths.get("uploads");

  // Metoda pobierająca użytkownika po ID z cache
  @Override
  @Cacheable(value = "userEntityById")
  public UserEntity getUserById(final String userId) {
    return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
  }

  // Metoda dodająca użytkownika
  @Override
  public void addUser(final UserRequestDto userRequestDto) {
    final UserEntity user = modelMapper.map(userRequestDto, UserEntity.class);
    isMailAllreadyUsed(user); // Sprawdzenie, czy email już istnieje
    user.setId(null);
    user.setPassword(passwordEncoder.encryptPassword(userRequestDto.getPassword())); // Ustawienie hasła
    user.setEmail(userRequestDto.getEmail()); // Ustawienie adresu email
    user.setName(userRequestDto.getName()); // Ustawienie imienia
    user.setSurname(userRequestDto.getSurname()); // Ustawienie nazwiska
    user.setRole(Role.NORMAL);
    userRepository.save(user); // Zapisanie do bazy danych
   // createActivateAccToken(userRequestDto);
    //sendActivateAccountEmail(userRequestDto);
  }

  // Metoda logowania użytkownika
  @Override
  public UserLoginResponseDto loginUser(final String email, final String password) {
    return userRepository
            .findByEmail(email)
            .map(x -> loginUserAndReturnBearerToken(x, password))
            .orElseThrow(UserNotFoundException::new);
  }

  // Metoda usuwająca użytkownika
  @Override
  public void deleteUser(final String id) {
    userRepository.deleteById(id);
  }

  // Metoda zwracająca listę użytkowników na podstawie nazwiska
  @Override
  public List<UserEntity> getAllUsersList(final String surname) {
    return userRepository.findAllOrderBySurname(surname);
  }

  @Override
  public void addAdmin(AdminRequestDto dto) {
     final UserEntity entity = modelMapper.map(dto, UserEntity.class);
     isMailAllreadyUsed(entity);
    entity.setId(null);
    entity.setRole(Role.ADMIN);
    entity.setEmail(dto.getEmail());
    entity.setPassword(dto.getPassword());
    entity.setActivated(true);
     userRepository.save(entity);

  }

  @Override
  public void addModerator(AdminRequestDto dto) {
    final UserEntity entity = modelMapper.map(dto, UserEntity.class);
    isMailAllreadyUsed(entity);
    entity.setId(null);
    entity.setRole(Role.MODERATOR);
    entity.setEmail(dto.getEmail());
    entity.setPassword(dto.getPassword());
    entity.setActivated(true);
    userRepository.save(entity);
  }

  @Override
  public List<UserDetailsDto> getAllUsersDetails() {
    List<UserDetailsDto> userDetailsList = new ArrayList<>();
    List<UserEntity> userList = userRepository.findAll();

    for (UserEntity user : userList) {
      UserDetailsDto userDetailsDto = new UserDetailsDto();
      userDetailsDto.setFirstName(user.getName());
      userDetailsDto.setLastName(user.getSurname());
      userDetailsList.add(userDetailsDto);
    }

    return userDetailsList;
  }

  @Override
  public String resetPassword(ResetPasswordDto dto, MailRequest request) {
    UserEntity entity = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(
                    UserNotFoundException::new
            );
   // entity.setPassword(dto.getNewPassword());
    if (!Objects.equals(dto.getNewPassword(), dto.getConfirmNewPassword())){
      throw new IdenticalPasswordException();
    }
    if (passwordEncoder.matchPassword(entity.getPassword(),dto.getNewPassword())){
      throw new SameAsOldPasswordException();
    }
    setNewPwd(request,entity);

  return "Haslo zostało zmienione";
  }

  @Override
  public void sendResetPasswordEmail( MailRequest dto) {
   final EmailTemplate template;
    UserEntity entity = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(
                    UserNotFoundException::new
            );
    Context context = new Context();
    context.setVariable("resetLink", createPasswordResetToken(dto));

    // Generujemy treść emaila z użyciem szablonu Thymeleaf dla resetowania hasła
    String emailContent = templateEngine.process(EmailTemplate.RESET_TEMPLATE.getName(), context);

    // Tworzymy obiekt wiadomości email
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("kolozbik1948@gmail.com");
    mailMessage.setTo(entity.getEmail());
    mailMessage.setSubject("Reset Password");
    mailMessage.setText(emailContent);

    // Wysyłamy wiadomość email
    mailSender.send(mailMessage);
  }

  @Override
  public void logout() {

  }

  private void sendActivateAccountEmail(UserRequestDto dto){
    final EmailTemplate template;
    final UserEntity entity = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(
                    UserNotFoundException::new
            );
    Context context = new Context();
    context.setVariable("activationLink", createActivateAccToken(dto));

    // Generujemy treść emaila z użyciem szablonu Thymeleaf dla resetowania hasła
    String emailContent = templateEngine.process(EmailTemplate.ACTIVATE_TEMPLATE.getName(), context);

    // Tworzymy obiekt wiadomości email
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("Koło łowieckie żbik");
    mailMessage.setTo(entity.getEmail());
    mailMessage.setSubject("Aktywacja konta");
    mailMessage.setText(emailContent);

    // Wysyłamy wiadomość email
    mailSender.send(mailMessage);
    entity.setActivated(true);
  }

  //private void sendResetPasswordEmail(UserEntity entity){

private void setNewPwd( MailRequest dto, UserEntity entity){
    createPasswordResetToken(dto);
}

  private String createPasswordResetToken(MailRequest dto) {
    Optional<UserEntity> userOptional = userRepository.findByEmail(dto.getEmail());
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException();
    }
    UserEntity user = userOptional.get();
    // Generate token
    String token = UUID.randomUUID().toString();
    // Set token expiry (e.g., 0,5 hour from now)
    Date expiryDate = new Date(System.currentTimeMillis() + 1800000);
    // Create TokenEntity
    TokenEntity tokenEntity = new TokenEntity();
    tokenEntity.setToken(token);
    tokenEntity.setTokenType(TokenType.RESET);
    tokenEntity.setExpiryDate(expiryDate);
    tokenEntity.setExpired(false);
    // Save token
    tokenRepository.save(tokenEntity);
    user.setResetToken(token);
    userRepository.save(user);
    return token;
  }


  private String createActivateAccToken(UserRequestDto dto) {
    Optional<UserEntity> userOptional = userRepository.findByEmail(dto.getEmail());
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException();
    }
    UserEntity user = userOptional.get();
    // Generate token
    String token = UUID.randomUUID().toString();
    // Set token expiry (e.g., 30 dni od teraz)
    long thirtyDaysInMillis = 30L * 24 * 60 * 60 * 1000;
    Date expiryDate = new Date(System.currentTimeMillis() + thirtyDaysInMillis);    // Create TokenEntity
    TokenEntity tokenEntity = new TokenEntity();
    tokenEntity.setToken(token);
    tokenEntity.setTokenType(TokenType.ACTIVATION );
    tokenEntity.setExpiryDate(expiryDate);
    tokenEntity.setExpired(false);
    // Save token
    tokenRepository.save(tokenEntity);
    user.setResetToken(token);
    userRepository.save(user);
    return token;
  }

  // Metoda sprawdzająca, czy mail jest już używany
  private void isMailAllreadyUsed(final UserEntity user) {
    Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());
    existingUser.ifPresent(
            x -> {
              throw new UserAlreadyExistException();
            });
  }


  // Metoda logowania użytkownika i zwracająca token JWT
  private UserLoginResponseDto loginUserAndReturnBearerToken(
          final UserEntity entity, final String password) {

    if (!passwordEncoder.matchPassword(entity.getPassword(), password))
      throw new PasswordDoesNotMatchException();

    final UserJWT jwt = new UserJWT();
    jwt.setId(entity.getId());
    jwt.setEmail(entity.getEmail());

    final String encodedJwt = tokenEncoder.generateBearerJwtTokenFromModel(jwt);
    final UserLoginResponseDto responseObject = new UserLoginResponseDto();
    responseObject.setEmail(entity.getEmail());
    responseObject.setToken(encodedJwt);

    return responseObject;
  }


  // Metoda ustawiająca szczegóły użytkownika
  private UserEntity setUserDetails(final UserRequestDto dto) {
    final UserEntity userEntity = modelMapper.map(dto, UserEntity.class);
    userEntity.setId(null);
    userEntity.setName(dto.getName());
    userEntity.setSurname(dto.getSurname());
    userEntity.setActivated(true);
    userEntity.setEmail(dto.getEmail());
    return userEntity;
  }
  }



