package pl.pkociolek.zbik.service;

import pl.pkociolek.zbik.model.dtos.mail.MailRequest;
import pl.pkociolek.zbik.model.dtos.request.*;
import pl.pkociolek.zbik.model.dtos.token.TokenDto;
import pl.pkociolek.zbik.model.dtos.user.ResetPasswordDto;
import pl.pkociolek.zbik.model.dtos.user.UserLoginResponseDto;
import pl.pkociolek.zbik.model.dtos.user.UserDetailsDto;
import pl.pkociolek.zbik.model.dtos.user.UserRequestDto;
import pl.pkociolek.zbik.repository.entity.UserEntity;

import java.util.List;

public interface UserService {
  UserEntity getUserById(String userId);

  void addUser(UserRequestDto userRequestDto);

  UserLoginResponseDto loginUser(String email, String password);
  void deleteUser(String id);
  List<UserEntity> getAllUsersList(String surname);
  void addAdmin(AdminRequestDto dto);
  void addModerator(AdminRequestDto dto);
  List<UserDetailsDto> getAllUsersDetails();
  public String resetPassword(ResetPasswordDto dto, MailRequest request);
  public void sendResetPasswordEmail( MailRequest dto);
  public void logout();


}
