package com.memegenerator.backend.web.dto;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RequestResponse {
    public String Message;
    public List<String> Errors = new ArrayList<String>();
    public Boolean Success = false;
}
