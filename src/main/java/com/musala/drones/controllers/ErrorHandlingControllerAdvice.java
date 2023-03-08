package com.musala.drones.controllers;

import com.musala.drones.exceptions.DroneBatteryLevelException;
import com.musala.drones.exceptions.DroneLoadException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler({ConstraintViolationException.class, DroneLoadException.class, DroneBatteryLevelException.class})
    public ProblemDetail handleConstraintViolationException(RuntimeException exception) {
        var pd = ProblemDetail.forStatus(HttpStatusCode.valueOf(403));
        pd.setDetail(exception.getMessage());
        return pd;
    }
}
