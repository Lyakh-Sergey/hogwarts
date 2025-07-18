package ru.hogwarts.school.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.utils.TestUtils.*;

@SuppressWarnings("ALL")
@DataJpaTest
class FacultyRepositoryTest {

    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    void findByColor_ShouldReturnFaculties() {
        Faculty faculty = createTestFaculty(null, "Gryffindor", "red");
        facultyRepository.save(faculty);

        List<Faculty> result = (List<Faculty>) facultyRepository.findByColor("red");

        assertEquals(1, result.size());
        assertEquals("Gryffindor", result.get(0).getName());
    }

    @Test
    void findByNameIgnoreCaseOrColorIgnoreCase_ShouldReturnFaculties() {
        Faculty faculty = createTestFaculty(null, "Gryffindor", "red");
        facultyRepository.save(faculty);

        List<Faculty> result = facultyRepository
                .findByNameIgnoreCaseOrColorIgnoreCase("gryffindor", "blue");

        assertEquals(1, result.size());
    }
}
