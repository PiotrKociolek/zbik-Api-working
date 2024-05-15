package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.DatabaseEntityIsNotExistException;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.model.dtos.docs.DocsUpdate;
import pl.pkociolek.zbik.model.dtos.request.DocsRequestDto;
import pl.pkociolek.zbik.repository.DocumentsRepository;
import pl.pkociolek.zbik.repository.entity.DocsEntity;
import pl.pkociolek.zbik.service.DocsService;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class DocsServiceImpl implements DocsService {
    private final DocumentsRepository repository;
    private final ModelMapper modelMapper;
    private final Path root = Paths.get("uploads/docs");


    @Override
    public void addDocs(MultipartFile file, DocsRequestDto dto){
        final  DocsEntity entity = modelMapper.map(dto, DocsEntity.class);
        uploadFolderExists();
        try {
            final DocsEntity docsEntity= setDocsDetails(file,dto);
            Files.copy(
                    file.getInputStream(),
                    this.root.resolve(Objects.requireNonNull(getFileNameANdExtension(docsEntity))),
                    StandardCopyOption.REPLACE_EXISTING);
            repository.save(docsEntity);
        } catch (final Exception e) {
            throw new FileAlreadyExistsException();
        }
        repository.save(entity);
    }

    @Override
    public void update(DocsUpdate dto) {
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
        docs.setDescription(dto.getDescription());
        docs.setObfuscatedFileName(generateUniqueFileName());
        String[] extension = file.getOriginalFilename().split("\\.");
        docs.setFileExtension(extension[extension.length-1]);
        return  docs;
    }

    private static String generateUniqueFileName()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        return "file_" + timestamp ;
    }

    private String getFileNameANdExtension(final DocsEntity docs) {
        final String filename = docs.getObfuscatedFileName();
        final String extension = docs.getFileExtension();

        return String.format("%s.%s", filename, extension);
    }
    private void updateDescFunction (DocsEntity docs, DocsUpdate dto){
        docs.setDescription(dto.getDescription());
        repository.save(docs);
    }

    private void uploadFolderExists() {
        if (!Files.exists(root)) {
            try {
                Files.createDirectories(root);
            } catch (Exception e) {
                throw new RuntimeException("Nie można utworzyć katalogu upload");
            }
        }
    }
}
