package com.hg.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse(
        @JsonProperty(value = "code") String code,
        @JsonProperty(value = "errors") List<ErrorDTO> errors,
        @JsonProperty(value = "data") Object data) {

    public ApiResponse(String code, List<ErrorDTO> errors, Object data) {
        this.code = code;
        this.errors = List.copyOf(errors);
        this.data = data;
    }
}
