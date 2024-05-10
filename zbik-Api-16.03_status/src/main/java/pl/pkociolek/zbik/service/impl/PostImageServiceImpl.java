package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.exception.ImageNotFoundException;
import pl.pkociolek.zbik.model.dtos.request.CreatePostDto;
import pl.pkociolek.zbik.model.dtos.request.UpdatePostDto;
import pl.pkociolek.zbik.repository.ImageRepository;
import pl.pkociolek.zbik.repository.entity.ImageEntity;
import pl.pkociolek.zbik.service.PostImageService;

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
public class PostImageServiceImpl implements PostImageService {

    private final Path root = Paths.get("uploads");
    private final ImageRepository repository;
    private final ModelMapper modelMapper;


    @Override
    public void addPostImg(MultipartFile file, CreatePostDto dto) {
        try {
            final ImageEntity imageEntity = setImageDetails(file, dto);
            Files.copy(
                    file.getInputStream(),
                    this.root.resolve(Objects.requireNonNull(getFileNameAndExtension(imageEntity))),
                    StandardCopyOption.REPLACE_EXISTING);
            repository.save(imageEntity);
        } catch (final Exception e) {
            throw new FileAlreadyExistsException();
        }
    }
    @Override
    public void updatePostImg(MultipartFile file, UpdatePostDto dto) {
        try {
            final ImageEntity imageEntity = setImageDetailsUpdate(file, dto);
            Files.copy(
                    file.getInputStream(),
                    this.root.resolve(Objects.requireNonNull(getFileNameAndExtension(imageEntity))),
                    StandardCopyOption.REPLACE_EXISTING);
            repository.save(imageEntity);
        } catch (final Exception e) {
            throw new FileAlreadyExistsException();
        }
    }

    @Override
    public void deleteImage(String imageId) {
        repository.deleteById(imageId);
    }

    @Override
    public ImageEntity getImageById(String imageId) {
        Optional<ImageEntity> optionalImageEntity = repository.findById(imageId);
        return optionalImageEntity.orElseThrow(() -> new ImageNotFoundException());
    }


    private ImageEntity setImageDetails(final MultipartFile file, final CreatePostDto dto) {
        final ImageEntity imageEntity = modelMapper.map(dto, ImageEntity.class);
        imageEntity.setId(null);
        imageEntity.setFileName(file.getOriginalFilename());
        imageEntity.setObfuscatedFileName(generateFilename());
        return imageEntity;
    }
    private ImageEntity setImageDetailsUpdate(final MultipartFile file, final UpdatePostDto dto) {
        final ImageEntity imageEntity = modelMapper.map(dto, ImageEntity.class);
        imageEntity.setId(null);
        imageEntity.setFileName(file.getOriginalFilename());
        imageEntity.setObfuscatedFileName(generateFilename());
        return imageEntity;
    }

    private String generateFilename() {
        final byte[] array = new byte[31];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private String getFileNameAndExtension(final ImageEntity imageEntity) {
        final String filename = imageEntity.getObfuscatedFileName();
        final String extension = imageEntity.getFileExtension();
        return String.format("%s.%s", filename, extension);
    }
}
