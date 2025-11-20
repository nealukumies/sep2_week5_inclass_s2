package org.example;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private Handler testHandler;

    @BeforeEach
    void setUp() {
        // Redirect logger output
        Logger logger = App.logger;
        testHandler = new StreamHandler(outContent, new SimpleFormatter());
        logger.addHandler(testHandler);
        logger.setUseParentHandlers(false); // Don't log to console
    }

    @AfterEach
    void tearDown() {
        App.logger.removeHandler(testHandler);
    }

    @Test
    void testAskInt() {
        String simulatedInput = "5\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int result = App.askInt();
        assertEquals(5, result);

        System.setIn(originalIn);
    }

    @Test
    void testRunLoop() {
        App.runLoop(3);

        // Flush the logger to ensure all messages are written
        testHandler.flush();
        String output = outContent.toString();

        assertTrue(output.contains("x = 1"));
        assertTrue(output.contains("x = 2"));
        assertTrue(output.contains("x = 3"));
    }
}