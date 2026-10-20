package com.autorent.application.dto.response;



public record ApiErrorResponse(
        int status,
        String error,
        String message
) {
}
