package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.pkociolek.zbik.model.dtos.request.RIPRequestDto;
import pl.pkociolek.zbik.model.dtos.request.RIPUpdateDto;
import pl.pkociolek.zbik.repository.entity.InMemoryEntity;
import pl.pkociolek.zbik.service.InMemoryService;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/InMemory")
public class InMemoryController {
    private final InMemoryService service;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addMemberToInMemory(@RequestBody RIPRequestDto dto) {
        service.addToMemorialPage(dto);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateInMemory(@RequestParam String id, @RequestBody RIPUpdateDto dto) {
        service.updateInMemory(id, dto);
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteInMemory(@RequestParam String id) {
        service.delete(id);
    }
    @GetMapping(value ="/getInMemoryList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<InMemoryEntity> getInmemoryList(){
        return service.getListOfAll();
    }
}
