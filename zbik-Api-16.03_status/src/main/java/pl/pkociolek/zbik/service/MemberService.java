package pl.pkociolek.zbik.service;

import pl.pkociolek.zbik.model.dtos.members.MembersDto;
import pl.pkociolek.zbik.model.dtos.members.MembersUpdateDto;
import pl.pkociolek.zbik.repository.entity.MembersEntity;

import java.util.List;

public interface MemberService {
    void addToMemberList(MembersDto dto);
    void update(String id, MembersUpdateDto dto);
    List<MembersEntity> getListOfAll();
    void delete(String id);
}
