package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.GalleryRequestDto;
import pl.pkociolek.zbik.model.dtos.request.UserRequestDto;
import pl.pkociolek.zbik.model.dtos.response.UserLoginResponseDto;
import pl.pkociolek.zbik.service.GalleryService;
import pl.pkociolek.zbik.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
  private final GalleryService galleryService;
  @PostMapping(value = "/login/{username}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public UserLoginResponseDto loginUser(
          @PathVariable("username") final String username,
          @PathVariable("password") final String password) {
    return userService.loginUser(username, password);
  }
  @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public void addUser(@RequestBody final UserRequestDto dto) {
    userService.addUser(dto);
  }



  @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteUserById(@PathVariable("id") final String id) {
    userService.deleteUser(id);
  }


}

