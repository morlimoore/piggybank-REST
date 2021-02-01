package com.morlimoore.piggybankapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.morlimoore.piggybankapi.util.TransactionEnum;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionInfoDTO {
    private Long transactionId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp date;
    private TransactionEnum type;
    private Long amount;
    private String remarks;
}