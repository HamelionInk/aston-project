package com.nikitin.astonproject.service;

import com.nikitin.astonproject.dto.filter.CardOperationFilter;
import com.nikitin.astonproject.dto.request.CardOperationDepositRequestDto;
import com.nikitin.astonproject.dto.request.CardOperationRequestDto;
import com.nikitin.astonproject.dto.request.CardOperationTransferRequestDto;
import com.nikitin.astonproject.dto.request.CardOperationWithdrawRequestDto;
import com.nikitin.astonproject.dto.response.CardOperationResponseDto;
import com.nikitin.astonproject.entity.CardOperation;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardOperationService {

    CardOperationResponseDto create(@NonNull CardOperationRequestDto cardOperationRequestDto);
    CardOperation getEntityById(@NonNull Long id, @NonNull Boolean withError);
    CardOperationResponseDto getResponseById(@NonNull Long id);
    Page<CardOperationResponseDto> getAll(@NonNull CardOperationFilter filter, @NonNull Pageable pageable);
    CardOperationResponseDto depositByNumberCard(@NonNull CardOperationDepositRequestDto cardOperationDepositRequestDto);
    CardOperationResponseDto withdrawByNumberCard(@NonNull CardOperationWithdrawRequestDto cardOperationWithdrawRequestDto);
    CardOperationResponseDto transferByNumberCard(@NonNull CardOperationTransferRequestDto cardOperationTransferRequestDto);
}
