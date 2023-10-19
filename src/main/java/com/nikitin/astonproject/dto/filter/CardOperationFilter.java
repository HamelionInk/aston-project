package com.nikitin.astonproject.dto.filter;

import com.nikitin.astonproject.entity.enums.OperationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardOperationFilter {

    @Schema(description = "Filter by ids", example = "[\"32\", \"12\"]")
    private List<Long> ids;

    @Schema(description = "Filter by cardIds", example = "[\"12\", \"8\"]")
    private List<Long> cardIds;

    @Schema(description = "Filter by greater amount operation", example = "123.43")
    private BigDecimal greaterAmountOperation;

    @Schema(description = "Filter by less amount operation", example = "432.43")
    private BigDecimal lessAmountOperation;

    @Schema(description = "Filter by operation type", example = "DEPOSIT")
    private OperationType operationType;
}
