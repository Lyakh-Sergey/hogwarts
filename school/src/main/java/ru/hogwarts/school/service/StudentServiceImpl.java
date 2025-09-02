package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final FacultyServiceInterface facultyService;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyServiceInterface facultyService) {
        this.studentRepository = studentRepository;
        this.facultyService = facultyService;
    }

    @Override
    public Integer getTotalNumberOfStudents() {
        logger.info("Was invoked method for getting total number of students");
        return studentRepository.getTotalNumberOfStudents();
    }

    @Override
    public Double getAverageAge() {
        logger.info("Was invoked method for getting average age of students");
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> findLastFiveStudents() {
        logger.info("Was invoked method for finding last five students");
        return studentRepository.findLastFiveStudents();
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Was invoked method for creating student: {}", student.getName());
        if (student.getFaculty() != null) {
            Faculty faculty = facultyService.findFaculty(student.getFaculty().getId());
            if (faculty == null) {
                logger.error("Faculty with id={} not found for student creation", student.getFaculty().getId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Faculty not found");
            }
            student.setFaculty(faculty);
        }
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        logger.info("Was invoked method for finding student by id={}", id);
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student editStudent(Student student) {
        logger.info("Was invoked method for editing student with id={}", student.getId());
        if (!studentRepository.existsById(student.getId())) {
            logger.error("Student with id={} not found for editing", student.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with id: " + student.getId());
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        logger.info("Was invoked method for deleting student with id={}", id);
        Student student = findStudent(id);
        if (student != null) {
            studentRepository.deleteById(id);
            logger.debug("Student with id={} successfully deleted", id);
        } else {
            logger.warn("Attempt to delete non-existing student with id={}", id);
        }
    }

    @Override
    public List<Student> findByAge(int age) {
        logger.info("Was invoked method for finding students by age={}", age);
        return studentRepository.findByAge(age);
    }

    @Override
    public List<Student> findByNameIgnoreCase(String name) {
        logger.info("Was invoked method for finding students by name={}", name);
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method for finding students between ages {} and {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public List<String> getStudentNamesStartingWithA() {
        logger.info("Was invoked method for getting student names starting with A");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.toUpperCase().startsWith("А"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeStudents() {
        logger.info("Was invoked new method for getting average age of students");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }
}