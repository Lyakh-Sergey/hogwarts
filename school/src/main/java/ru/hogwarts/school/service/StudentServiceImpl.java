package ru.hogwarts.school.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;


@Service
public class StudentServiceImpl implements StudentServiceInterface {
    private final StudentRepository studentRepository;
    private final FacultyServiceInterface facultyService;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyServiceInterface facultyService) {
        this.studentRepository = studentRepository;
        this.facultyService = facultyService;
    }

    @Override
    public Student createStudent(Student student) {
        if (student.getFaculty() != null) {
            Faculty faculty = facultyService.findFaculty(student.getFaculty().getId());
            if (faculty == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Faculty not found");
            }
            student.setFaculty(faculty);
        }
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student editStudent(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with id: " + student.getId());
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        Student student = findStudent(id);
        if (student != null) {
            studentRepository.deleteById(id);
        }
    }

    @Override
    public List<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public List<Student> findByNameIgnoreCase(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }
}