package com.nikitin.astonproject.dto.filter;

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
@Schema(description = "Account filter dto")
public class AccountFilter {

    @Schema(description = "Filter by ids", example = "[\"54\", \"4\"]")
    private List<Long> ids;

    @Schema(description = "Filter by names", example = "[\"Michael\", \"Denis\"]")
    private List<String> names;
}
