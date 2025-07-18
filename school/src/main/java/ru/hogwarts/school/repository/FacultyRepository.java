package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColorContainingIgnoreCase(String color);
    List<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String name, String color);

    Object findByColor(String red);

    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String gryffindor, String blue);
}
