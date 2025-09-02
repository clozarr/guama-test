package com.guama.purchases.shared.exception;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.net.URI;
import java.util.List;

@Data
@Builder
@ToString
public class ErrorResponse {


    private Integer code;
    private String reason;
    private List<String> messages;
    private String date;
    private URI path;

}