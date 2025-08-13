package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByAgeBetween(int minAge, int maxAge);


    Optional<Student> findById(long id);
}
