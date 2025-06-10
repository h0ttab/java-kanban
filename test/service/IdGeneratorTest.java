package service;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class IdGeneratorTest {
    IdGenerator idGenerator = new IdGenerator();

    @Test
    @DisplayName("Генератор должен увеличивать внутренний счётчик ID на +1 после каждой генерации")
    void shouldReturnUniqueId() {
        int expectedFirstId = 1;
        assertEquals(expectedFirstId, idGenerator.generateId());

        int expected100thId = 100;
        for (int i = 0; i < 98; i++) {
            idGenerator.generateId();
        }
        assertEquals(expected100thId, idGenerator.generateId());
    }
}
