package com.tks.grocerymono.base.dto.response;

import com.tks.grocerymono.base.constant.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {

    public <T> ResponseEntity<BaseResponse<T>> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(true);
        response.setCode(HttpStatus.OK.value());
        response.setData(data);
        response.setMessage(Message.SUCCESS);
        return ResponseEntity.ok(response);
    }

    public <T> ResponseEntity<BaseResponse<T>> error(int code, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(getResponseDataError(code, message, null));
    }

    public <T> BaseResponse<T> getResponseDataError(
            int code, String message, T data) {
        BaseResponse<T> response = new BaseResponse<T>();
        response.setSuccess(true);
        response.setCode(code);
        response.setMessage(message.isBlank() ? Message.UNKNOWN_ERROR : message);
        response.setData(data);
        return response;
    }

    public <T> ResponseEntity<BaseResponse<T>> error(HttpStatus status) {
        return ResponseEntity.status(status).body(getResponseDataError(status.value(), status.getReasonPhrase(), null));
    }
}
