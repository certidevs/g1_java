package com.demo.dto;


import lombok.*;

import java.time.LocalDate;

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
