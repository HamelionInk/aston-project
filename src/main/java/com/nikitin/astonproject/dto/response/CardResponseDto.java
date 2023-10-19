package com.nikitin.astonproject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardResponseDto {

    @Schema(description = "Идентификатор карты", example = "43")
    private Long id;

    @Schema(description = "Номер карты", example = "8459 4321 9401 0341")
    private Long numberCard;

    @Schema(description = "Сумма бюджета", example = "104000,54")
    private BigDecimal amountBudget;
}
