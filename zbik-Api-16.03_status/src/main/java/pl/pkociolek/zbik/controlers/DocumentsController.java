package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.docs.DocsUpdate;
import pl.pkociolek.zbik.model.dtos.request.DocsRequestDto;
import pl.pkociolek.zbik.service.DocsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentsController {

    private final DocsService docsService;

    @PostMapping(value = "/add" ,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    void save(@RequestParam(value = "file")MultipartFile file, @ModelAttribute DocsRequestDto dto){
        docsService.addDocs(file, dto);
    };
    @PutMapping(value = "/edit/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    void editDescription(@RequestBody DocsUpdate dto){
        docsService.update(dto);
    };
    @DeleteMapping(value ="/documents/delete/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteById(@PathVariable("id") String id){
        docsService.deleteById(id);
    };
}
