package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void findByAge_ReturnsStudents() {
        Student student = new Student(1L, "Harry", 11);
        when(studentRepository.findByAge(11)).thenReturn(List.of(student));

        List<Student> result = studentService.findByAge(11);

        assertEquals(1, result.size());
        assertEquals(student, result.get(0));
    }
}
