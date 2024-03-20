package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.MapRequestDto;
import pl.pkociolek.zbik.service.MapsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/maps")
public class MapsController {
    final MapsService mapsService;

    @PutMapping(value = "/maps/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void addMap(@RequestParam final MultipartFile file, @RequestBody final MapRequestDto mapRequestDto) {
        mapsService.addMap(file, mapRequestDto);
    }

    @DeleteMapping(value = "/maps/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteMapById(@PathVariable("id") final String id) {
        mapsService.deleteMapById(id);
    }

    @PutMapping(value = "/maps/add-desc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    void addDescription(@RequestBody final MapRequestDto mapRequestDto) {
        mapsService.addDescription(mapRequestDto);
    }
}
