package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.pkociolek.zbik.model.dtos.request.AdminRequestDto;
import pl.pkociolek.zbik.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AddSuperusers {
    private final UserService userService;

    @PostMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void addAdmin(@RequestBody AdminRequestDto dto){
        userService.addAdmin(dto);{userService.addAdmin(dto);}
    }
    @PostMapping(value = "/moderator", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void addModerator(@RequestBody AdminRequestDto dto){
        userService.addModerator(dto);{userService.addAdmin(dto);}
    }
}
