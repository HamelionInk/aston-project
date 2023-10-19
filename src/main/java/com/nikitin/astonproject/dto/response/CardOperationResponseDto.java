package com.nikitin.astonproject.dto.response;

import com.nikitin.astonproject.entity.enums.OperationType;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class CardOperationResponseDto {

    @Schema(description = "Идентификатор истории", example = "1")
    private Long id;

    @Schema(description = "Сумма операции", example = "5407,45")
    private BigDecimal amountOperation;

    @Schema(description = "Дата совершения операции", example = "2023-08-28T09:18:55.766Z")
    private Instant dateOperation;

    @Schema(description = "Тип операции", example = "DEPOSIT")
    private OperationType operationType;

    @Schema(description = "Информация по карте после совершения операции")
    private CardResponseDto card;
}
