package pl.pkociolek.zbik.service.impl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.components.PasswordEncoder;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.exception.PasswordDoesNotMatchException;
import pl.pkociolek.zbik.exception.UserAlreadyExistException;
import pl.pkociolek.zbik.exception.UserNotFoundException;
import pl.pkociolek.zbik.model.Role;
import pl.pkociolek.zbik.model.dtos.request.AdminRequestDto;
import pl.pkociolek.zbik.model.dtos.request.UserRequestDto;
import pl.pkociolek.zbik.model.dtos.response.UserJWT;
import pl.pkociolek.zbik.model.dtos.response.UserLoginResponseDto;
import pl.pkociolek.zbik.repository.UserRepository;
import pl.pkociolek.zbik.repository.entity.UserEntity;
import pl.pkociolek.zbik.service.UserService;
import pl.pkociolek.zbik.utilities.jwt.JwtTokenEncoder;

@Service
@Transactional
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

  // Pola serwisu
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenEncoder tokenEncoder;
  private final UserRepository userRepository;
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
    isMailAllreadyUsed(user);
    user.setId(null);
    userRepository.save(user);
  }

  // Metoda logowania użytkownika
  @Override
  public UserLoginResponseDto loginUser(final String username, final String password) {
    return userRepository
        .findByUsername(username)
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
    entity.setId(null);
    entity.setRole(dto.getRole());
     userRepository.save(entity);

  }


  // Metoda sprawdzająca, czy mail jest już używany
  private void isMailAllreadyUsed(final UserEntity user) {
    userRepository
        .findByUsernameAndEmailAddress(user.getUsername(), user.getEmailAddress())
        .ifPresent(
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
    jwt.setEmail(entity.getEmailAddress());

    final String encodedJwt = tokenEncoder.generateBearerJwtTokenFromModel(jwt);
    final UserLoginResponseDto responseObject = new UserLoginResponseDto();
    responseObject.setEmail(entity.getEmailAddress());
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
    userEntity.setEmailAddress(dto.getEmail());
    return userEntity;
  }

  }

  // Metoda generująca nazwę pliku


