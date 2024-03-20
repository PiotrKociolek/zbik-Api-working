package pl.pkociolek.zbik.service;

import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.CalendarRequestDto;
import pl.pkociolek.zbik.model.dtos.response.HuntingCalendarDto;

public interface HuntingCalendarService {
    void addDescription(HuntingCalendarDto huntingCalendarDto);
    void addSpecies(HuntingCalendarDto huntingCalendarDto);

    void editDescription(HuntingCalendarDto huntingCalendarDto);
    void deleteFromTable(String id);
    void save(MultipartFile file, CalendarRequestDto dto);

}
