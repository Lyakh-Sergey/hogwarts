package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;
import java.util.List;

public interface StudentServiceInterface {
    Student createStudent(Student student);
    Student findStudent(long id);
    Student editStudent(Student student);
    void deleteStudent(long id);
    List<Student> findByAge(int age);
    List<Student> findByNameIgnoreCase(String name);
    List<Student> findByAgeBetween(int minAge, int maxAge);

    Integer getTotalNumberOfStudents();
    Double getAverageAge();
    List<Student> findLastFiveStudents();
}
