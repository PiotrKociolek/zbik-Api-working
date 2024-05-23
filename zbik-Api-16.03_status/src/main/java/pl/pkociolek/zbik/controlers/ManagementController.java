package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.ManagementImgDto;
import pl.pkociolek.zbik.model.dtos.response.ManagementInfoDto;
import pl.pkociolek.zbik.service.ManagementService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management")
public class ManagementController {
    private final ManagementService managementService;


    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    void deleteFromManagement(@RequestParam String id) {
        managementService.deleteFromManagement(id);
    }


   @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   @ResponseStatus(HttpStatus.OK)
   public void addToManagement(@RequestParam("file") MultipartFile file,@ModelAttribute("Info") ManagementInfoDto dto) {
     managementService.addToManagement(dto, file);
   }

}
