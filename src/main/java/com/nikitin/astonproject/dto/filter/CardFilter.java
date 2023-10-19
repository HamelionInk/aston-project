package com.nikitin.astonproject.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Card filter dto")
public class CardFilter {

    @Schema(description = "Filter by ids", example = "[\"1\", \"4\"]")
    private List<Long> ids;

    @Schema(description = "Filter by account ids", example = "[\"5\", \"11\"]")
    private List<Long> accountIds;

    @Schema(description = "Filter by numberCards", example = "[\"8459342445674806\", \"7459342445674801\"]")
    private List<Long> numberCards;

    @Schema(description = "Filter by greater date create", example = "2023-08-28T09:18:55.766Z")
    private Instant greaterDateCreate;

    @Schema(description = "Filter by less date create", example = "2023-08-28T09:18:55.766Z")
    private Instant lessDateCreate;
}
