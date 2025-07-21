package com.bci.usermanagement.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserException {
    private String message;
}
