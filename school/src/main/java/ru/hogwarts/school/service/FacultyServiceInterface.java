package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyServiceInterface {
    Faculty createFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty editFaculty(Faculty faculty);

    void deleteFaculty(long id);

    List<Faculty> findByColorIgnoreCase(String color);

    List<Faculty> findByNameOrColorIgnoreCase(String name, String color);

    Object getFacultyById(long l);

    Object getFacultiesByColor(String red);
}
