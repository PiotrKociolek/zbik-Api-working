package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.CalendarRequestDto;
import pl.pkociolek.zbik.model.dtos.response.HuntingCalendarDto;
import pl.pkociolek.zbik.service.HuntingCalendarService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/huntingCalendar")
public class CalendarController {
  private final HuntingCalendarService calendarService;

/*  @PutMapping(value = "/huntingCalendar/add", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  void addDescription(@RequestBody final HuntingCalendarDto huntingCalendarDto) {
    calendarService.addItemToCalendar(huntingCalendarDto);
  }

  @PutMapping(value = "/huntingCalendar/addSpecies", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  void addSpecies(@RequestBody final HuntingCalendarDto huntingCalendarDto) {
    calendarService.addSpecies(huntingCalendarDto);
  }

  @PutMapping(value = "/huntingCalendar/addDescription", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  void editDescription(@RequestBody final HuntingCalendarDto huntingCalendarDto) {
    calendarService.editDescription(huntingCalendarDto);
  }

  @DeleteMapping(value = "/huntingCalendar/delete", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  void deleteFromTable(@PathVariable("id") final String id) {
    calendarService.deleteFromTable(id);
  }

  @PutMapping(
      value = "/huntingCalendar/save",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public void save( @RequestParam(value = "file") final MultipartFile file, @RequestBody final CalendarRequestDto dto) {
    calendarService.addIcon(file, dto);
  }*/
}
