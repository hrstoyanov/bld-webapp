package insbiz.webapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InswebappTest {
    @Test
    void verifyHello() {
        assertEquals("Hello World!", new InswebappMain().getMessage());
    }
}
