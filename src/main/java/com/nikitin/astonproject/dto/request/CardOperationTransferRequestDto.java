package com.nikitin.astonproject.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikitin.astonproject.entity.enums.OperationType;
import com.nikitin.astonproject.validation.Create;
import com.nikitin.astonproject.validation.Update;
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
@Schema(description = "Card operation for transfer request dto")
public class CardOperationTransferRequestDto {

    @Schema(description = "The card from which the amount is transferred", example = "3943934701237401")
    @NotNull(message = "Field numberCard cannot be null")
    @Max(value = 9999999999999999L, message = "Field producerNumberCard must contain 16 characters")
    @Min(value = 1000000000000000L, message = "Field producerNumberCard must contain 16 characters")
    private Long producerNumberCard;

    @Schema(description = "Card to which the amount is transferred", example = "4098437461037648")
    @NotNull(message = "Field numberCard cannot be null")
    @Max(value = 9999999999999999L, message = "Field consumerNumberCard must contain 16 characters")
    @Min(value = 1000000000000000L, message = "Field consumerNumberCard must contain 16 characters")
    private Long consumerNumberCard;

    @Schema(description = "Transaction amount", example = "500.234")
    @NotNull(message = "Field amount cannot be null")
    @Positive(message = "Field amount cannot be negative value")
    private BigDecimal amount;

    @Schema(description = "Transaction confirmation PIN", example = "4312")
    @NotNull(message = "Field pinCode cannot be null", groups = Create.class)
    @Max(value = 9999, message = "Field pinCode must contain 4 characters", groups = { Create.class, Update.class })
    @Min(value = 1000, message = "Field pinCode must contain 4 characters", groups = { Create.class, Update.class })
    private Integer pinCode;

    @JsonIgnore
    @Builder.Default
    private OperationType operationType = OperationType.TRANSFER;
}
