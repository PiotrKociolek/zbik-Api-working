package pl.pkociolek.zbik.service;

import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.calendar.UpdateCalendarDto;
import pl.pkociolek.zbik.model.dtos.request.AddHuntingCalendarDto;
import pl.pkociolek.zbik.model.dtos.request.CalendarRequestDto;
import pl.pkociolek.zbik.model.dtos.response.HuntingCalendarDto;

public interface HuntingCalendarService {

    void addItemToCalendar(HuntingCalendarDto huntingCalendarDto, MultipartFile file);
    void updateCalendarItem(UpdateCalendarDto dto, MultipartFile file);
    void delete (String id);


}
