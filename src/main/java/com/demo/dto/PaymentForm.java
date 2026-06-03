package com.demo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "El titular es obligatorio")
    private String cardOwner;

    @NotBlank(message = "El número de tarjeta es obligatorio")
    @Pattern(regexp = "\\d{16}", message = "El número de tarjeta debe tener 16 dígitos")
    private String cardNumber;

    @NotBlank(message = "La fecha de caducidad es obligatoria")
    @Pattern(regexp = "\\d{2}/\\d{2}", message = "La caducidad debe tener formato MM/YY")
    private String cardExpirationDate;

    @NotBlank(message = "El código de seguridad es obligatorio")
    @Pattern(regexp = "\\d{3}", message = "El código de seguridad debe tener 3 dígitos")
    private String cardCode;


}
