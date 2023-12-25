package com.saleh.cors_esaxon.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saleh.cors_esaxon.query.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Operation {
    @Id
    private Long id ;
    private Date date ;
    private double amount ;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
}
