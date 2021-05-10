package com.memegenerator.backend.web.dto;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResponse {
    
    public RequestResponse(String message) {
        this.message = message;
    }

    public String message;

    public List<String> errors = new ArrayList<String>();

    public Boolean success = false;
}
