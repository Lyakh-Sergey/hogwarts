package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyServiceInterface {
    Faculty createFaculty(Faculty faculty);
    Faculty findFaculty(long id);
    Faculty editFaculty(Faculty faculty);
    Faculty deleteFaculty(long id);
    List<Faculty> findByColor(String color);
}
