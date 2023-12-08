package org.project.balabolkka.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult<T> {
    int code;
    T data;
}
