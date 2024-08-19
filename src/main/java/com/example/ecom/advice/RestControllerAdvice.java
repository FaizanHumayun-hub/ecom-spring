package com.example.ecom.advice;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;


// RestControllerAdvice annotation
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ControllerAdvice
@ResponseBody
public @interface RestControllerAdvice {

}
