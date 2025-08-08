package ru.hogwarts.school.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarServiceInterface;

import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarServiceInterface avatarService;

    public AvatarController(AvatarServiceInterface avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> uploadAvatar(@PathVariable Long studentId,
                                             @RequestParam MultipartFile file) throws IOException {
        try {
            Long avatarId = avatarService.uploadAvatar(studentId, file);
            return ResponseEntity.ok(avatarId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{studentId}/from-db")
    public ResponseEntity<?> getAvatarFromDb(@PathVariable Long studentId) {
        try {
            Avatar avatar = avatarService.getAvatarFromDb(studentId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            headers.setContentLength(avatar.getFileSize());
            return ResponseEntity.ok().headers(headers).body(avatar.getData());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{studentId}/from-disk")
    public ResponseEntity<byte[]> getAvatarFromDisk(@PathVariable Long studentId) {
        try {
            byte[] avatarData = avatarService.getAvatarFromDisk(studentId);
            Avatar avatar = avatarService.getAvatarFromDb(studentId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            headers.setContentLength(avatar.getFileSize());

            return ResponseEntity.ok().headers(headers).body(avatarData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}