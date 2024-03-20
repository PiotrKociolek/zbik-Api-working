package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.DocsRequestDto;
import pl.pkociolek.zbik.repository.DocumentsRepository;
import pl.pkociolek.zbik.service.DocsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentsController {

    private final DocsService docsService;
    @PutMapping(value = "/documents/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    void addDesc (@RequestBody DocsRequestDto dto){
        docsService.addDesc(dto);
    };
    @PutMapping(value = "/documents/add" ,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    void save(@RequestParam(value = "file")MultipartFile file, @RequestBody DocsRequestDto dto){
        docsService.save(file, dto);
    };
    @PutMapping(value = "/documents/edit",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    void editDescription(@RequestBody DocsRequestDto dto){
        docsService.editDescription(dto);
    };
    @DeleteMapping(value ="/documents/delete/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteById(@PathVariable("id") String id){
        docsService.deleteById(id);
    };
}
