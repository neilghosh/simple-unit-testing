package com.example.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;  
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppTest {

    private App app;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setup() {
        app = new App(logger);
    }

    @ParameterizedTest
    @CsvSource(value = { "2, 3, 5", "1, 1, 2" }, delimiter = ',')
    public void testAdd(final int x, final int y, final int sum) {

        doNothing().when(logger).info(any(String.class));

        assertEquals(sum, app.add(x, y));

        verify(logger, times(1)).info((startsWith("Adding numbers")));
    }

    @Test
    public void testAddSafely() {
        final Exception exception = assertThrows(ArithmeticException.class, () -> app.addSafely(Integer.MAX_VALUE, 1));
        assertEquals("integer overflow", exception.getMessage());
    }
}
