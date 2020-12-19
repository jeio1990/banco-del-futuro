package com.appgate.risk.controller.error;

import java.time.LocalDateTime;

public class EntityApiError {

    private EntityErrorType entityErrorType;
    private String message;
    private LocalDateTime timestamp;


    public EntityApiError(EntityErrorType entityErrorType, String message) {
        this.entityErrorType = entityErrorType;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public EntityErrorType getEntityErrorType() {
        return entityErrorType;
    }

    public void setEntityErrorType(EntityErrorType entityErrorType) {
        this.entityErrorType = entityErrorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
