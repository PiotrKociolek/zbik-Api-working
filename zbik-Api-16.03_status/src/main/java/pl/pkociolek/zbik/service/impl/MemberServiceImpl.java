package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pkociolek.zbik.exception.EntityNotFoundException;
import pl.pkociolek.zbik.model.dtos.members.MembersDto;
import pl.pkociolek.zbik.model.dtos.members.MembersUpdateDto;
import pl.pkociolek.zbik.repository.MemberRepository;
import pl.pkociolek.zbik.repository.entity.MembersEntity;
import pl.pkociolek.zbik.repository.entity.UserEntity;
import pl.pkociolek.zbik.service.MemberService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;
    private final MemberRepository repository;

    @Override
    public void addToMemberList(MembersDto dto) {
        final MembersEntity entity = modelMapper.map(dto, MembersEntity.class);
        entity.setId(null);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        repository.save(entity);
    }

    @Override
    public void update(String id, MembersUpdateDto dto) {
        Optional<MembersEntity> optionalEntity = repository.findById(id);

        if (optionalEntity.isPresent()) {
            MembersEntity entity = optionalEntity.get();
            entity.setName(dto.getName());
            entity.setSurname(dto.getSurname());
            repository.save(entity);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<MembersEntity> getListOfAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}

