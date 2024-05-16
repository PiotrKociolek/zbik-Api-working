package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.CannotCreateUploadFolderException;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.model.dtos.request.MapRequestDto;
import pl.pkociolek.zbik.repository.MapsRepository;
import pl.pkociolek.zbik.repository.entity.MapsEntity;
import pl.pkociolek.zbik.service.MapsService;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MapsServiceImpl implements MapsService {
private final MapsRepository repository;
private final ModelMapper modelMapper;
    private final Path root = Paths.get("uploads");
    @Override
    public void addMap(MultipartFile file, MapRequestDto dto){
        final  MapsEntity mapsEntity = modelMapper.map(dto,MapsEntity.class);
        uploadFolderExists();
        try {
            final MapsEntity entity= setMapDetails(file,dto);
            Files.copy(
                    file.getInputStream(),
                    this.root.resolve(Objects.requireNonNull(getFileNameANdExtension(entity))),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (final Exception e) {
            throw new FileAlreadyExistsException();
        }
        repository.save(mapsEntity);
    }

    @Override
    public void deleteMapById(String id) {
         repository.deleteById(id);
    }




    private MapsEntity setMapDetails(
            final MultipartFile file, final MapRequestDto dto){
        final MapsEntity entity = modelMapper.map(dto, MapsEntity.class);
        entity.setId(null);
        entity.setDescription(dto.getDescription());
        entity.setObfuscatedFileName(generateUniqueFileName());
        String[] extension = file.getOriginalFilename().split("\\.");
        entity.setFileExtension(extension[extension.length-1]);
        return entity;
    }
    private static String generateUniqueFileName()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        return "file_" + timestamp ;
    }

    private String getFileNameANdExtension(final MapsEntity gallery) {
        final String filename = gallery.getObfuscatedFileName();
        final String extension = gallery.getFileExtension();

        return String.format("%s.%s", filename, extension);
    }
    private void uploadFolderExists() {
        if (!Files.exists(root)) {
            try {
                Files.createDirectories(root);
            } catch (Exception e) {
                throw new CannotCreateUploadFolderException();
            }
        }
}
}
