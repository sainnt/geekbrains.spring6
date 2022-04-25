package com.sainnt.homework6hibernate.exception;

public class DaoException extends RuntimeException{
    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
