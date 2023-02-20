package br.com.carv.essentials.exception;

import java.time.LocalDateTime;

public class ExceptionMessageValidator extends ExceptionResponse {

    private String fields;
    private String fieldsMessage;

    public ExceptionMessageValidator() {
    }

    public ExceptionMessageValidator(String title, Integer status, String details, String developerMessage, LocalDateTime timestamp,
                                     String fields, String fieldsMessage) {
        super(title, status, details, developerMessage, timestamp);
        this.fields = fields;
        this.fieldsMessage = fieldsMessage;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getFieldsMessage() {
        return fieldsMessage;
    }

    public void setFieldsMessage(String fieldsMessage) {
        this.fieldsMessage = fieldsMessage;
    }
}
