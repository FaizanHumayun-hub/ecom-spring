package com.example.ecom.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.security.SignatureException;
import java.time.Instant;


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Access Denied: " + exc.getMessage());

//        ObjectMapper mapper = new ObjectMapper();
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("timestamp", Instant.now().toString());
//        jsonObject.put("status", HttpStatus.FORBIDDEN.value());
//        jsonObject.put("error", HttpStatus.FORBIDDEN.getReasonPhrase());
//        jsonObject.put("message","********* custom message what you want ********");
//        jsonObject.put("path", request.getRequestURI());
//
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        response.getWriter().write( mapper.writeValueAsString(jsonObject));
    }
}
