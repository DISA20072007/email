package ru.kkb.isimple;

import java.io.Serializable;

/**
 * @author denis.fedorov
 */
public class Response implements Serializable {

    public static final Response RESPONSE_OK = new Response(true);

    private boolean success;
    private String errorMessage;

    private Response(boolean success) {
        this.success = success;
    }

    public Response(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
