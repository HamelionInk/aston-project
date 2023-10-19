package com.nikitin.astonproject.dto.request;

import com.nikitin.astonproject.entity.enums.OperationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Card operation request dto")
public class CardOperationRequestDto {

    @Schema(description = "Transaction amount", example = "5407,45")
    @NotNull(message = "Field amountOperation cannot be null")
    @Positive(message = "Field amountOperation cannot be negative value")
    private BigDecimal amountOperation;

    @Schema(description = "Transaction date", example = "2023-08-28T09:18:55.766Z")
    @FutureOrPresent(message = "Date operation cannot be in the past")
    private Instant dateOperation;

    @Schema(description = "Operation type", example = "DEPOSIT")
    @NotNull(message = "Filed operationType cannot be null")
    private OperationType operationType;

    @Schema(description = "Identifier card", example = "5")
    @NotNull(message = "Field cardId cannot be null")
    @Positive(message = "Field cardId cannot be negative")
    private Long cardId;
}
