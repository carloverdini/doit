package it.unicam.doit.controller.helper;

import it.unicam.doit.service.ApiErrorResponse;
import it.unicam.doit.service.ApiResponse;

public class AbstractApiController {


    protected ApiResponse getSuccess(Object data){
        ApiResponse r = new ApiResponse();
        r.success = true;
        r.data = data;
        return r;
    }

    protected ApiErrorResponse getError(String message){
        return new ApiErrorResponse(message);
    }
}
