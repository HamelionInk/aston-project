package com.nikitin.astonproject.controller;

import com.nikitin.astonproject.dto.filter.CardFilter;
import com.nikitin.astonproject.dto.request.CardRequestDto;
import com.nikitin.astonproject.dto.response.CardResponseDto;
import com.nikitin.astonproject.exception.ExceptionResponseDto;
import com.nikitin.astonproject.service.CardService;
import com.nikitin.astonproject.validation.Create;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
@Tag(name = "Card")
public class CardController {

    private final CardService cardService;

    @Operation(description = "Create card for account", method = "POST")
    @ApiResponse(responseCode = "201", description = "CREATED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CardResponseDto.class))})
    @ApiResponse(responseCode = "400", description = "BAD REQUEST",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @PostMapping
    public ResponseEntity<CardResponseDto> create(@RequestBody @Validated(value = Create.class) CardRequestDto cardRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cardService.create(cardRequestDto));
    }

    @Operation(description = "Get card by id", method = "GET")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CardResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(cardService.getResponseById(id));
    }

    @Operation(description = "Get all card or get all with filters", method = "GET")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CardResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @PageableAsQueryParam
    @GetMapping
    public ResponseEntity<Page<CardResponseDto>> getAll(@ModelAttribute CardFilter cardFilter,
                                                        @ParameterObject @PageableDefault(sort = "dateCreate",
                                                                direction = Sort.Direction.ASC,
                                                                size = Integer.MAX_VALUE) Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(cardService.getAll(cardFilter, pageable));
    }

    @Operation(description = "Delete card by id", method = "DELETE")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CardResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id) {
        cardService.deleteById(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }
}
