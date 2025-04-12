package org.pchardwarestore.dto;

import java.util.ArrayList;
import java.util.List;

public class GeneralResponse<T> {
    private T body;
    private List<String> errors;

    public GeneralResponse(T body) {
        this.body = body;
        this.errors = new ArrayList<>();
    }

    public T getBody() {
        return body;
    }
    public List<String> getErrors() {
        return errors;
    }
    public void addError(String error) {
        errors.add(error);
    }
}
