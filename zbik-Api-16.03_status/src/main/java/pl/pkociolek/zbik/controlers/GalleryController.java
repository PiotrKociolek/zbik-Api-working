package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.GalleryRequestDto;
import pl.pkociolek.zbik.repository.entity.GalleryEntity;
import pl.pkociolek.zbik.service.GalleryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery")
public class GalleryController {
final GalleryService galleryService;
    @PostMapping(
            value = "/gallery/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void addGallery(
            @RequestBody final GalleryRequestDto galleryRequestDto,
            @RequestParam(value = "file") final MultipartFile file) {
        galleryService.save(file, galleryRequestDto);
    }

    @DeleteMapping(
            value ="/gallery/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void deleteGalleryById(@PathVariable("id") String  id){
        galleryService.deleteById(id);
    };
    @PutMapping (value = " /gallery/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    void modify(GalleryRequestDto galleryRequestDto, MultipartFile file){
        galleryService.modify(galleryRequestDto,file);
    };
    @GetMapping (value = " /gallery/",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    List<GalleryEntity> getAllPhotos(){
        return galleryService.getAllPhotos();
    };
}
