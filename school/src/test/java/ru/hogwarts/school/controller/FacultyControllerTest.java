package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyServiceImpl;
import ru.hogwarts.school.service.FacultyServiceInterface;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyControllerTest {

    @Mock
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    private Faculty testFaculty;

    @BeforeEach
    void setUp() {
        testFaculty = new Faculty(1L, "Gryffindor", "red");
    }

    @Test
    void createFaculty_ShouldReturnCreatedFacultyWithStatus200() {
        // Подготовка
        when(facultyService.createFaculty(any(Faculty.class)))
                .thenReturn(testFaculty);

        // Выполнение
        Faculty response = facultyController.createFaculty(testFaculty);

        // Проверки
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(testFaculty, response.getBody()),
                () -> verify(facultyService, times(1)).createFaculty(any(Faculty.class))
        );
    }

    @Test
    void getFacultyById_ShouldReturnFacultyWithStatus200() {
        // Подготовка
        when(facultyService.getFacultyById(1L))
                .thenReturn(testFaculty);

        // Выполнение
        ResponseEntity<Faculty> response = facultyController.getFaculty(1L);

        // Проверки
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(testFaculty, response.getBody())
        );
    }
}
