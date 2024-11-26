package com.tks.grocerymono.exception;


import com.tks.grocerymono.base.dto.response.BaseResponse;
import com.tks.grocerymono.base.dto.response.ResponseData;
import com.tks.grocerymono.base.dto.response.ResponseFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestControllerAdvice
public class RestExceptionHandler {
    private final ResponseFactory responseFactory;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<Void>> handleBaseException(BaseException baseException) {
        return responseFactory.error(baseException.getCode(), baseException.getMessage(), baseException.getHttpStatus());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<BaseResponse<Void>> handleNoSuchElementException(NoSuchElementException noSuchElementException) {
        return responseFactory.error(400, noSuchElementException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<BaseResponse<Void>> handleSQLException(SQLException sqlException) {
        if (sqlException.getMessage().contains("Duplicate entry"))
            return responseFactory.error(400, "Duplicate Resource Exception", HttpStatus.BAD_REQUEST);
        else {
            sqlException.printStackTrace();
            return responseFactory.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
