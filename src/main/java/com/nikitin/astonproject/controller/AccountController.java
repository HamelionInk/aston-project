package com.nikitin.astonproject.controller;

import com.nikitin.astonproject.dto.filter.AccountFilter;
import com.nikitin.astonproject.dto.request.AccountRequestDto;
import com.nikitin.astonproject.dto.response.AccountResponseDto;
import com.nikitin.astonproject.exception.ExceptionResponseDto;
import com.nikitin.astonproject.service.AccountService;
import com.nikitin.astonproject.validation.Create;
import com.nikitin.astonproject.validation.Update;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Tag(name = "Account")
public class AccountController {

    private final AccountService accountService;


    @Operation(description = "Create account for user", method = "POST")
    @ApiResponse(responseCode = "201", description = "CREATED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AccountResponseDto.class))})
    @ApiResponse(responseCode = "400", description = "BAD REQUEST",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @PostMapping
    public ResponseEntity<AccountResponseDto> create(@RequestBody @Validated(value = Create.class) AccountRequestDto accountRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.create(accountRequestDto));
    }

    @Operation(description = "Update account for user", method = "PATCH")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AccountResponseDto.class))})
    @ApiResponse(responseCode = "400", description = "BAD REQUEST",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @PatchMapping("/{id}")
    public ResponseEntity<AccountResponseDto> update(@PathVariable(name = "id") Long id,
                                                     @RequestBody @Validated(value = Update.class) AccountRequestDto accountRequestDto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(accountService.update(id, accountRequestDto));
    }

    @Operation(description = "Get account by id", method = "GET")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AccountResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(accountService.getResponseById(id));
    }

    @Operation(description = "Get all accounts or get all with filters", method = "GET")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AccountResponseDto.class))})
    @PageableAsQueryParam
    @GetMapping
    public ResponseEntity<Page<AccountResponseDto>> getAll(@ModelAttribute AccountFilter accountFilter,
                                                           @ParameterObject @PageableDefault(sort = "id",
                                                                   direction = Sort.Direction.ASC,
                                                                   size = Integer.MAX_VALUE) Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(accountService.getAll(accountFilter, pageable));
    }

    @Operation(description = "Delete account by id", method = "DELETE")
    @ApiResponse(responseCode = "202", description = "ACCEPTED",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AccountResponseDto.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDto.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id) {
        accountService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }
}
