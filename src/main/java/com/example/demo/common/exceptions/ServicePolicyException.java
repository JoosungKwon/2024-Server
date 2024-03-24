package com.example.demo.common.exceptions;

import com.example.demo.common.model.response.BaseResponseStatus;

public class ServicePolicyException extends BaseException {
    public ServicePolicyException(BaseResponseStatus status) {
        super(status);
    }

}
