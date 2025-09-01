package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for creating faculty: {}", faculty.getName());
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for finding faculty by id={}", id);
        return facultyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Faculty with id={} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found");
                });
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for editing faculty with id={}", faculty.getId());
        if (!facultyRepository.existsById(faculty.getId())) {
            logger.error("Faculty with id={} not found for editing", faculty.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found with id: " + faculty.getId());
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(long id) {
        logger.info("Was invoked method for deleting faculty with id={}", id);
        Faculty faculty = findFaculty(id);
        if (faculty != null) {
            facultyRepository.deleteById(id);
            logger.debug("Faculty with id={} successfully deleted", id);
        }
    }

    @Override
    public List<Faculty> findByNameOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method for searching faculties by name={} or color={}", name, color);
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
    }

    @Override
    public Faculty findFacultyWithStudents(long id) {
        logger.info("Was invoked method for finding faculty with students by id={}", id);
        return facultyRepository.findByIdWithStudents(id)
                .orElseThrow(() -> {
                    logger.error("Faculty with id={} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found");
                });
    }
}

