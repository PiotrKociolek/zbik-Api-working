package pl.pkociolek.zbik.service;

import pl.pkociolek.zbik.model.dtos.request.*;
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
  List<UserDetailsDto> getAllUsersDetails();


}
