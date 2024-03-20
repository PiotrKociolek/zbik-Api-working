package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.DatabaseEntityIsNotExistException;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.model.dtos.request.DocsRequestDto;
import pl.pkociolek.zbik.model.dtos.request.GalleryRequestDto;
import pl.pkociolek.zbik.model.dtos.request.UpdatePostDto;
import pl.pkociolek.zbik.repository.DocumentsRepository;
import pl.pkociolek.zbik.repository.MapsRepository;
import pl.pkociolek.zbik.repository.entity.DocsEntity;
import pl.pkociolek.zbik.repository.entity.GalleryEntity;
import pl.pkociolek.zbik.repository.entity.MapsEntity;
import pl.pkociolek.zbik.repository.entity.PostEntity;
import pl.pkociolek.zbik.service.DocsService;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class DocsServiceImpl implements DocsService {
    private final DocumentsRepository repository;
    private final ModelMapper modelMapper;
    private final Path root = Paths.get("uploads");
    @Override
    public void addDesc(DocsRequestDto dto) {
        final DocsEntity mapsEntity = modelMapper.map(dto, DocsEntity.class);
        mapsEntity.setId(null);
        repository.save(mapsEntity);
    }

    @Override
    public void save(MultipartFile file, DocsRequestDto dto){
        try {
            final DocsEntity uEntity= setDocsDetails(file,dto);
            Files.copy(
                    file.getInputStream(),
                    this.root.resolve(Objects.requireNonNull(getFileNameANdExtension(uEntity))),
                    StandardCopyOption.REPLACE_EXISTING);
            repository.save(uEntity);
        } catch (final Exception e) {
            throw new FileAlreadyExistsException();
        }
    }

    @Override
    public void  editDescription(DocsRequestDto dto) {
        final Optional<DocsEntity> postEntity =repository.findById(dto.getId());
        postEntity.ifPresentOrElse(x->updateDescFunction(x,dto), DatabaseEntityIsNotExistException::new);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
    private DocsEntity setDocsDetails(
            final MultipartFile file, final DocsRequestDto dto) {
        final DocsEntity docs =  modelMapper.map(dto, DocsEntity.class);
        docs.setId(null);
        docs.setObfuscatedFileName(generateFilename());
        return  docs;
    }

    private String generateFilename() {
        final byte[] array = new byte[15];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private String getFileNameANdExtension(final DocsEntity docs) {
        final String filename = docs.getObfuscatedFileName();
        final String extension = docs.getFileExtension();

        return String.format("%s.%s", filename, extension);
    }
    private void updateDescFunction (DocsEntity docs, DocsRequestDto dto){
        docs.setDescription(dto.getDescription());
        repository.save(docs);
    }
    }
