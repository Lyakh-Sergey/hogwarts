package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import java.io.IOException;
import java.util.List;

public interface AvatarServiceInterface {
    void uploadAvatar(Long studentId, MultipartFile file) throws IOException;
    Avatar findAvatar(Long studentId);
    List<Avatar> getAvatarsByPage(int page, int size);
}