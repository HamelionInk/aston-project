package com.nikitin.astonproject.controller;

import com.nikitin.astonproject.aspect.annotation.SecurePinCode;
import com.nikitin.astonproject.dto.filter.CardOperationFilter;
import com.nikitin.astonproject.dto.request.CardOperationDepositRequestDto;
import com.nikitin.astonproject.dto.request.CardOperationTransferRequestDto;
import com.nikitin.astonproject.dto.request.CardOperationWithdrawRequestDto;
import com.nikitin.astonproject.dto.response.CardOperationResponseDto;
import com.nikitin.astonproject.exception.ExceptionResponseDto;
import com.nikitin.astonproject.service.CardOperationService;
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
@RequestMapping("/card-operations")
@Tag(name = "Card operation")
public class CardOperationController {

    private final CardOperationService cardOperationService;

    @Operation(description = "Get cardOperation by id", method = "GET")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CardOperationResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @GetMapping("/{id}")
    public ResponseEntity<CardOperationResponseDto> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(cardOperationService.getResponseById(id));
    }

    @Operation(description = "Get all cardOperation or get all with filters", method = "GET")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CardOperationResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @PageableAsQueryParam
    @GetMapping
    public ResponseEntity<Page<CardOperationResponseDto>> getAll(@ModelAttribute CardOperationFilter cardOperationFilter,
                                                                 @ParameterObject @PageableDefault(sort = "dateOperation",
                                                                         direction = Sort.Direction.ASC,
                                                                         size = Integer.MAX_VALUE) Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(cardOperationService.getAll(cardOperationFilter, pageable));
    }

    @Operation(description = "Deposit by number card", method = "POST")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CardOperationResponseDto.class))})
    @ApiResponse(responseCode = "400", description = "BAD REQUEST",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @PostMapping("/deposit")
    public ResponseEntity<CardOperationResponseDto> depositByNumberCard(@RequestBody @Validated CardOperationDepositRequestDto cardOperationDepositRequestDto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(cardOperationService.depositByNumberCard(cardOperationDepositRequestDto));
    }

    @Operation(description = "Withdraw by number card", method = "POST")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CardOperationResponseDto.class))})
    @ApiResponse(responseCode = "400", description = "BAD REQUEST",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @PostMapping("/withdraw")
    @SecurePinCode
    public ResponseEntity<CardOperationResponseDto> withdrawByNumberCard(@RequestBody @Validated CardOperationWithdrawRequestDto cardOperationWithdrawRequestDto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(cardOperationService.withdrawByNumberCard(cardOperationWithdrawRequestDto));
    }

    @Operation(description = "Transfer by number card", method = "POST")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CardOperationResponseDto.class))})
    @ApiResponse(responseCode = "400", description = "BAD REQUEST",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @PostMapping("/transfer")
    @SecurePinCode
    public ResponseEntity<CardOperationResponseDto> transferByNumberCard(@RequestBody @Validated CardOperationTransferRequestDto cardOperationTransferRequestDto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(cardOperationService.transferByNumberCard(cardOperationTransferRequestDto));
    }
}
