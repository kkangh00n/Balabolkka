package org.project.balabolkka.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidErrorResult {

    String field;

    String value;

    String message;

}
