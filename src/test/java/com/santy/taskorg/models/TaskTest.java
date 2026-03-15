package com.santy.taskorg.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTaskIsValidWhenDataIsCorrect() {
        Task task = new Task();
        task.setTitle("Aprender Spring Boot");
        task.setDueDate(LocalDate.now().plusDays(5)); // 5 days ahead

        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        assertTrue(violations.isEmpty(), "La tarea debería ser válida");
    }

    @Test
    public void testTaskIsInvalidWhenTitleIsEmpty() {
        Task task = new Task();
        task.setTitle(""); // Simulates empty title
        task.setDueDate(LocalDate.now());

        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        assertFalse(violations.isEmpty());

        // Checks error message
        assertEquals("Title cannot be empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testTaskIsInvalidWhenDateIsInThePast() {
        Task task = new Task();
        task.setTitle("Tarea válida");
        task.setDueDate(LocalDate.now().minusDays(1)); // Yesterday

        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        assertFalse(violations.isEmpty());
    }
}
