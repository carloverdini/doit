package it.unicam.doit.service;

public class ApiErrorResponse extends ApiResponse{

    public String message;

    public ApiErrorResponse(String message){
        this.success = false;
        this.message = message;
    }
}
