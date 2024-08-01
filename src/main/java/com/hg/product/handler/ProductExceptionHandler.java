package com.hg.product.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.hg.product.dto.ApiResponse;
import com.hg.product.dto.ErrorDTO;
import com.hg.product.exception.GenericException;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse> handleProductRequestValidationException(
            MethodArgumentNotValidException ex) {
        List<ErrorDTO> errorList = ex.getFieldErrors()
                .stream()
                .map(s -> new ErrorDTO(s.getCode(), s.getDefaultMessage()))
                .toList();
        ApiResponse apiResponse = new ApiResponse(null, errorList, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler({HttpMessageConversionException.class})
    public ResponseEntity<ApiResponse> handleHttpMessageConversionException(
            HttpMessageConversionException ex) {
        List<ErrorDTO> errorList = List.of(new ErrorDTO(ex.getLocalizedMessage(), ex.getMessage()));
        ApiResponse apiResponse = new ApiResponse(null, errorList, null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<ApiResponse> handleNoResourceFound(NoResourceFoundException ex) {
        List<ErrorDTO> errorList = List.of(new ErrorDTO(ex.getStatusCode().toString(), ex.getMessage()));
        ApiResponse apiResponse = new ApiResponse(null, errorList, null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler({GenericException.class})
    public ResponseEntity<ApiResponse> handleProductNotFound(GenericException ex) {
        List<ErrorDTO> errorList = List.of(new ErrorDTO(ex.getCode(), ex.getMessage()));
        ApiResponse apiResponse = new ApiResponse(null, errorList, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}
