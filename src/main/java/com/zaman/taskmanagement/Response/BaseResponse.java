package com.zaman.taskmanagement.Response;

import org.springframework.http.HttpStatus;

public class BaseResponse {
    private  Object data;
    private HttpStatus status;
    private  String errorMessage;

    public BaseResponse() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
