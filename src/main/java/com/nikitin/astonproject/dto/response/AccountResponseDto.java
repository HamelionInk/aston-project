package com.nikitin.astonproject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {

    @Schema(description = "Идентификатор аккаунта", example = "43")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Михаил")
    private String name;

    @Schema(description = "Пин-код", example = "4487")
    private Integer pinCode;

    @Schema(description = "Список карт сотрудников")
    private List<CardResponseDto> cards;
}
