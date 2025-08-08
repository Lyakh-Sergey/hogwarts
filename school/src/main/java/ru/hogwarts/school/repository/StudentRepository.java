package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByAgeBetween(int minAge, int maxAge);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.avatar a LEFT JOIN FETCH s.faculty WHERE s.id = :id")
    Optional<Student> findByIdWithDetails(@Param("id") long id);
}
