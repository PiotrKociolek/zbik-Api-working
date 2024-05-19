package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.pkociolek.zbik.model.dtos.user.UserRequestDto;
import pl.pkociolek.zbik.model.dtos.user.UserLoginResponseDto;
import pl.pkociolek.zbik.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
 @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
 @ResponseStatus(value = HttpStatus.OK)
 public UserLoginResponseDto login(@RequestParam("email") String email, @RequestParam("password") String password) {
     return userService.loginUser(email, password);
 }

  @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public void addUser(@RequestBody final UserRequestDto dto) {
    userService.addUser(dto);
  }

  @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteUserById(@PathVariable("id") final String id) {
    userService.deleteUser(id);
  }

@PostMapping(value = "/forgot-password", produces =  MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String forgotPassword(@RequestParam String email){
     return userService.forgotPassword(email);
}
}

