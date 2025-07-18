package ru.hogwarts.school.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void findByAge_ShouldReturnStudents() {
        Student student = new Student();
        student.setName("Test");
        student.setAge(11);
        studentRepository.save(student);

        List<Student> result = studentRepository.findByAge(11);

        assertEquals(1, result.size());
    }
}
