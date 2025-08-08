package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;

public interface AvatarServiceInterface {
    Long uploadAvatar(Long studentId, MultipartFile file) throws IOException;
    Avatar getAvatarFromDb(Long studentId);
    byte[] getAvatarFromDisk(Long studentId) throws IOException;
}
