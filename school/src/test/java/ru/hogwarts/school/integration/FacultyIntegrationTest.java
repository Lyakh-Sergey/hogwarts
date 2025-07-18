package ru.hogwarts.school.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.utils.TestUtils.*;

@SuppressWarnings("ALL")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    FacultyIntegrationTest(int port, TestRestTemplate restTemplate) {
        this.port = port;
        this.restTemplate = restTemplate;
    }

    @Test
    void createAndGetFaculty() {
        Faculty faculty = createTestFaculty(null, "Ravenclaw", "blue");

        ResponseEntity<Faculty> createdResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );

        assertEquals(200,
                createdResponse.getStatusCodeValue());
        assertNotNull(Objects.requireNonNull(createdResponse.getBody()).getId());

        ResponseEntity<Faculty> fetchedResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + createdResponse.getBody().getId(),
                Faculty.class
        );

        assertEquals("Ravenclaw", Objects.requireNonNull(fetchedResponse.getBody()).getName());
    }
}
