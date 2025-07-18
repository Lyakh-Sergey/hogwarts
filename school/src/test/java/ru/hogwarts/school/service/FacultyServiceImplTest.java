package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.utils.TestUtils.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    void createFaculty_SavesAndReturnsFaculty() {
        Faculty faculty = createTestFaculty(null, "Gryffindor", "red");
        Faculty savedFaculty = createTestFaculty(1L, "Gryffindor", "red");

        when(facultyRepository.save(faculty)).thenReturn(savedFaculty);

        Faculty result = facultyService.createFaculty(faculty);

        assertEquals(1L, result.getId());
        verify(facultyRepository).save(faculty);
    }

    @Test
    void getFacultiesByColor_ReturnsFilteredFaculties() {
        Faculty faculty = createTestFaculty(1L, "Gryffindor", "red");
        when(facultyRepository.findByColor("red")).thenReturn(List.of(faculty));

        List<Faculty> result = (List<Faculty>) facultyService.getFacultiesByColor("red");

        assertEquals(1, result.size());
        assertEquals("Gryffindor", result.get(0).getName());
    }

    @Test
    void findByNameOrColorIgnoreCase_ReturnsMatchingFaculties() {
        Faculty faculty = createTestFaculty(1L, "Gryffindor", "red");
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("gryffindor", "red"))
                .thenReturn(List.of(faculty));

        List<Faculty> result = facultyService.findByNameOrColorIgnoreCase("gryffindor", "red");

        assertEquals(1, result.size());
    }
}
