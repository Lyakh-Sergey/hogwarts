package ru.hogwarts.school.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createAndGetStudent() {
        Student student = new Student(null, "Integration", 10);

        Student created = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        assertNotNull(created.getId());

        Student fetched = restTemplate.getForObject(
                "http://localhost:" + port + "/student/" + created.getId(),
                Student.class
        );

        assertEquals(created.getName(), fetched.getName());
    }
}
