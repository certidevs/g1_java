package com.demo.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentForm {
    private String cardOwner;
    private String cardNumber;
    private String cardExpirationDate;
    private String cardCode;

    // getters y setters
}
