package com.example.ecom.advice;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

// ControllerAdvice annotation
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ControllerAdvice {

}