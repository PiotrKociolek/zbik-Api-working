package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.pkociolek.zbik.model.dtos.members.MembersDto;
import pl.pkociolek.zbik.model.dtos.members.MembersUpdateDto;
import pl.pkociolek.zbik.model.dtos.request.RIPUpdateDto;
import pl.pkociolek.zbik.repository.entity.MembersEntity;
import pl.pkociolek.zbik.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/membersList")
public class MemberListController {
    private MemberService service;
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addMember(@RequestBody MembersDto dto) {
        service.addToMemberList(dto);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateMember(@PathVariable String id, @RequestBody MembersUpdateDto dto) {
        service.update(id, dto);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteMember(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping(value ="/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<MembersEntity> getAllMembers() {
        return service.getListOfAll();
    }
}
