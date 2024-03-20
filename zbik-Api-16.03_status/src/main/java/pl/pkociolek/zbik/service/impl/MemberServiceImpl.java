package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pkociolek.zbik.exception.EntityNotFoundException;
import pl.pkociolek.zbik.model.dtos.request.MemberListDto;
import pl.pkociolek.zbik.repository.UserRepository;
import pl.pkociolek.zbik.repository.entity.UserEntity;
import pl.pkociolek.zbik.service.MemberService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;
    private final UserRepository repository;

    @Override
    public void addToMemberList(MemberListDto dto) {
        final UserEntity entity = modelMapper.map(dto, UserEntity.class);
        entity.setId(null);
        repository.save(entity);
    }

    @Override
    public void update(final String id, final String uName, final String uSurname) {
        Optional<UserEntity> optionalEntity = repository.findById(id);

        if (optionalEntity.isPresent()) {
            UserEntity entity = optionalEntity.get();
            entity.setSurname(uSurname);
            entity.setName(uName);
            repository.save(entity);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<UserEntity> getListOfAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}

