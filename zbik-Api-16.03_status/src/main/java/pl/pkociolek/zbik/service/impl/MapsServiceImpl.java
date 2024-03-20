package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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
import java.util.Objects;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class MapsServiceImpl implements MapsService {
private final MapsRepository repository;
private final ModelMapper modelMapper;
    private final Path root = Paths.get("uploads");
    @Override
    public void save(MultipartFile file, MapRequestDto dto){
        try {
            final MapsEntity mEntity= setMapDetails(file,dto);
            Files.copy(
                    file.getInputStream(),
                    this.root.resolve(Objects.requireNonNull(getFileNameANdExtension(mEntity))),
                    StandardCopyOption.REPLACE_EXISTING);
            repository.save(mEntity);
        } catch (final Exception e) {
            throw new FileAlreadyExistsException();
        }
    }

    @Override
    public void deleteMapById(String id) {
         repository.deleteById(id);
    }

    @Override
    public void addDescription(MapRequestDto mapRequestDto) {
        final MapsEntity mapsEntity = modelMapper.map(mapRequestDto, MapsEntity.class);
        mapsEntity.setId(null);
        repository.save(mapsEntity);
    }


    private MapsEntity setMapDetails(
            final MultipartFile file, final MapRequestDto dto){
        final MapsEntity MgmtEntity = modelMapper.map(dto, MapsEntity.class);
        MgmtEntity.setId(null);
        MgmtEntity.setObfuscatedFileName(generateFilename());
        return MgmtEntity;
    }
    private String generateFilename() {
        final byte[] array = new byte[31];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private String getFileNameANdExtension(final MapsEntity gallery) {
        final String filename = gallery.getObfuscatedFileName();
        final String extension = gallery.getFileExtension();

        return String.format("%s.%s", filename, extension);
    }
}
