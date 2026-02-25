package com.example.board.config;

public class ErrorResponse {

    private final boolean success;
    private final ErrorDetail error;

    private ErrorResponse(String code, String message) {
        this.success = false;
        this.error = new ErrorDetail(code, message);
    }

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public ErrorDetail getError() {
        return error;
    }

    public static class ErrorDetail {
        private final String code;
        private final String message;

        private ErrorDetail(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
