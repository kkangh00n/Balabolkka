package org.project.balabolkka.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public record ErrorResult (
    int code,
    String message
){

}
