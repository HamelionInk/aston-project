package com.nikitin.astonproject.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Schema(description = "Card request dto")
public class CardRequestDto {

    @Schema(description = "Budget amount", example = "104000,54")
    @NotNull(message = "Field amountBudget cannot be null")
    @Positive(message = "Field amountBudget cannot be negative")
    private BigDecimal amountBudget;

    @Schema(description = "Identifier account", example = "14")
    @NotNull(message = "Field accountId cannot be null")
    @Positive(message = "Field accountId cannot be negative")
    private Long accountId;
}
