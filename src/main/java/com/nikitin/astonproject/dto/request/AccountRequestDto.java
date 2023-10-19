package com.nikitin.astonproject.dto.request;

import com.nikitin.astonproject.validation.Create;
import com.nikitin.astonproject.validation.Update;
import com.nikitin.astonproject.validation.annotation.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Account request dto")
public class AccountRequestDto {

    @Schema(description = "Username", example = "Michael")
    @NotNull(message = "Field name cannot be null", groups = Create.class)
    @NotBlank(message = "", allowNull = true, groups = { Create.class, Update.class })
    private String name;

    @Schema(description = "PIN", example = "4487")
    @NotNull(message = "Field pinCode cannot be null", groups = Create.class)
    @Max(value = 9999, message = "Field pinCode must contain 4 characters", groups = { Create.class, Update.class })
    @Min(value = 1000, message = "Field pinCode must contain 4 characters", groups = { Create.class, Update.class })
    private Integer pinCode;
}
