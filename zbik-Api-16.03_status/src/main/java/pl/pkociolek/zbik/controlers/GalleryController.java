package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.Gallery.ModifyGalleryDto;
import pl.pkociolek.zbik.model.dtos.request.GalleryRequestDto;
import pl.pkociolek.zbik.repository.entity.GalleryEntity;
import pl.pkociolek.zbik.repository.entity.ImgEntity;
import pl.pkociolek.zbik.service.GalleryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery")
public class GalleryController {
final GalleryService galleryService;
    @PostMapping(
            value = "/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addGallery(
          @ModelAttribute GalleryRequestDto dto,@ModelAttribute MultipartFile miniature,@ModelAttribute MultipartFile[] files) {
        galleryService.addNewGallery(dto,miniature, files);
    }

    @DeleteMapping(
            value ="/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void deleteGalleryById(@PathVariable("id") String  id){
        galleryService.deleteById(id);
    };
    @PutMapping (value = " /update",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    void modify(ModifyGalleryDto dto, MultipartFile file){
        galleryService.modify(dto,file);
    };
    @GetMapping (value = "/",
    produces = MediaType.APPLICATION_JSON_VALUE)
   // consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    List<GalleryEntity> getAllPhotos(){
        return galleryService.getAllPhotos();
    }
}
