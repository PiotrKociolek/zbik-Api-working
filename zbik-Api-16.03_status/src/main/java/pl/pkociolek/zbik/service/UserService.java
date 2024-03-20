package pl.pkociolek.zbik.service;

import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.ManagementImgDto;
import pl.pkociolek.zbik.model.dtos.request.UserRequestDto;
import pl.pkociolek.zbik.model.dtos.response.UserLoginResponseDto;
import pl.pkociolek.zbik.repository.entity.UserEntity;

import java.util.List;

public interface UserService {
  UserEntity getUserById(String userId);

  void addUser(UserRequestDto userRequestDto);

  UserLoginResponseDto loginUser(String email, String password);

  void deleteUser(String id);
  List<UserEntity> getAllUsersList(String surname);
}
