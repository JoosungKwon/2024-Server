package com.example.demo.common.exceptions;

import com.example.demo.common.model.response.BaseResponseStatus;

public class NotFoundException extends BaseException {
    public NotFoundException(BaseResponseStatus status) {
        super(status);
    }

}
