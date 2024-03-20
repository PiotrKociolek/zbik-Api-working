package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.ManagementImgDto;
import pl.pkociolek.zbik.service.ManagementService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management")
public class ManagementController {
  private final ManagementService managementService;

  @DeleteMapping(value = "/management/delete", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  void deleteFromManagement(@RequestBody String id) {
    managementService.deleteFromManagement(id);
  }
  ;
  /* @GetMapping(value = "/management/add", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  void addToManagement(@RequestBody ){
      managementService.addToManagement();
  }*/
}
