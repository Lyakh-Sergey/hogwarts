package ru.hogwarts.school.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyServiceInterface {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found"));
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        if (!facultyRepository.existsById(faculty.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found with id: " + faculty.getId());
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(long id) {
        Faculty faculty = findFaculty(id);
        if (faculty != null) {
            facultyRepository.deleteById(id);
        }
    }

    @Override
    public List<Faculty> findByNameOrColorIgnoreCase(String name, String color) {
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
    }

}

