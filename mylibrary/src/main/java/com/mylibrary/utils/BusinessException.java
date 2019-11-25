package com.mylibrary.utils;

import java.util.Collection;

/**
 * Created by Annv on 4/27/18.
 */

public class BusinessException extends RuntimeException {
    private Collection<String> messages;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Exception cause) {
        super(msg, cause);
    }

    public BusinessException(Collection<String> messages) {
        super();
        this.messages = messages;
    }

    public BusinessException(Collection<String> messages, Exception cause) {
        super(cause);
        this.messages = messages;
    }

    @Override
    public String getMessage() {
        String msg = "";
        if (this.messages != null && !this.messages.isEmpty()) {
            for (String message : this.messages) {
                msg += message + ",";
            }
        } else msg = super.getMessage();
        return msg;
    }
}