package com.saleh.cors_esaxon.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatAccountRequestDTO {
    private double initialBalance;
    private String currency ;
}
