package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceInterface;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentServiceInterface studentService;

    public StudentController(StudentServiceInterface studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.editStudent(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) Integer age) {
        if (age != null) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Student>> getStudentsByName(@PathVariable String name) {
        List<Student> students = studentService.findByNameIgnoreCase(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/age-between")
    public ResponseEntity<List<Student>> getStudentsByAgeRange(
            @RequestParam int minAge,
            @RequestParam int maxAge) {
        List<Student> students = studentService.findByAgeBetween(minAge, maxAge);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null || student.getFaculty() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student.getFaculty());
    }

    @GetMapping("/count")
    public Integer getTotalNumberOfStudents() {
        return studentService.getTotalNumberOfStudents();
    }

    @GetMapping("/average-age")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/last-five")
    public List<Student> findLastFiveStudents() {
        return studentService.findLastFiveStudents();
    }

    @GetMapping("/names-starting-with-a")
    public List<String> getStudentNamesStartingWithA() {
        return studentService.getStudentNamesStartingWithA();
    }

    @GetMapping("/average-age-students")
    public Double getAverageAgeStudents() {
        return studentService.getAverageAgeStudents();
    }

    @GetMapping("/print-parallel")
    public void printStudentsParallel() {
        List<Student> students = studentService.getFirstSixStudents();

        if (students.size() < 6) {
            System.out.println("Not enough students in database. Need at least 6.");
            return;
        }

        // Первые два имени в основном потоке
        System.out.println("Main thread: " + Thread.currentThread().getName());
        studentService.printStudentName(students.get(0));
        studentService.printStudentName(students.get(1));

        // Третий и четвертый студент в параллельном потоке
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1: " + Thread.currentThread().getName());
            studentService.printStudentName(students.get(2));
            studentService.printStudentName(students.get(3));
        });

        // Пятый и шестой студент в другом параллельном потоке
        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2: " + Thread.currentThread().getName());
            studentService.printStudentName(students.get(4));
            studentService.printStudentName(students.get(5));
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Threads interrupted");
        }
    }

    @GetMapping("/print-synchronized")
    public void printStudentsSynchronized() {
        List<Student> students = studentService.getFirstSixStudents();

        if (students.size() < 6) {
            System.out.println("Not enough students in database. Need at least 6.");
            return;
        }

        // Первые два имени в основном потоке
        System.out.println("Main thread: " + Thread.currentThread().getName());
        studentService.printStudentNameSynchronized(students.get(0));
        studentService.printStudentNameSynchronized(students.get(1));

        // Третий и четвертый студент в параллельном потоке
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1: " + Thread.currentThread().getName());
            studentService.printStudentNameSynchronized(students.get(2));
            studentService.printStudentNameSynchronized(students.get(3));
        });

        // Пятый и шестой студент в другом параллельном потоке
        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2: " + Thread.currentThread().getName());
            studentService.printStudentNameSynchronized(students.get(4));
            studentService.printStudentNameSynchronized(students.get(5));
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Threads interrupted");
        }
    }
}
