package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceInterface;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentServiceInterface studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void createStudent_ShouldReturnCreatedStudentWithStatus200() {
        // Given
        Student inputStudent = new Student(null, "Harry Potter", 11);
        Student savedStudent = new Student(1L, "Harry Potter", 11);

        when(studentService.createStudent(any(Student.class)))
                .thenReturn(savedStudent);

        // When
        Student response = studentController.createStudent(inputStudent);

        // Then
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(savedStudent, response.getBody()),
                () -> assertEquals(1L, response.getBody().getId()),
                () -> verify(studentService, times(1)).createStudent(inputStudent),
                () -> verifyNoMoreInteractions(studentService)
        );
    }
}
