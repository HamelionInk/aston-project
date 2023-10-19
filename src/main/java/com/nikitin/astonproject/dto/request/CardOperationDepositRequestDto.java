package com.nikitin.astonproject.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikitin.astonproject.entity.enums.OperationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Card operation for deposit request dto")
public class CardOperationDepositRequestDto {

    @Schema(description = "Card number for transaction", example = "8459342445674806")
    @NotNull(message = "Field numberCard cannot be null")
    @Max(value = 9999999999999999L, message = "Field numberCard must contain 16 characters")
    @Min(value = 1000000000000000L, message = "Field numberCard must contain 16 characters")
    private Long numberCard;

    @Schema(description = "Transaction amount", example = "3453.13")
    @NotNull(message = "Field amount cannot be null")
    @Positive(message = "Field amount cannot be negative value")
    private BigDecimal amount;

    @JsonIgnore
    @Builder.Default
    private OperationType operationType = OperationType.DEPOSIT;
}
