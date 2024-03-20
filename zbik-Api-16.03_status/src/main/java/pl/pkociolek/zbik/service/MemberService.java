package pl.pkociolek.zbik.service;

import pl.pkociolek.zbik.model.dtos.request.MemberListDto;
import pl.pkociolek.zbik.repository.entity.UserEntity;

import java.util.List;

public interface MemberService {
    void addToMemberList(MemberListDto dto);
    void update(String id, String newFirstName, String newLastName);
    List<UserEntity> getListOfAll();
    void delete(String id);
}
