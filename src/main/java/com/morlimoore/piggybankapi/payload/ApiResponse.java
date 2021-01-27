package com.morlimoore.piggybankapi.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiResponse<T> {

    private HttpStatus status;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime time = LocalDateTime.now();
    private T result;

    public ApiResponse(HttpStatus status) {
        this.status = status;
    }

}

