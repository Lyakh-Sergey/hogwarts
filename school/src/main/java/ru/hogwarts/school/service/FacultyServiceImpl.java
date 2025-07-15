package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyServiceInterface {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long lastId = 0;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        faculties.put(lastId, faculty);
        return faculty;
    }

    @Override
    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }

    @Override
    public List<Faculty> findByColor(String color) {
        return faculties.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
