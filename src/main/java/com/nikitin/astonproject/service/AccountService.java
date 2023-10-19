package com.nikitin.astonproject.service;

import com.nikitin.astonproject.dto.filter.AccountFilter;
import com.nikitin.astonproject.dto.request.AccountRequestDto;
import com.nikitin.astonproject.dto.response.AccountResponseDto;
import com.nikitin.astonproject.entity.Account;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    AccountResponseDto create(@NonNull AccountRequestDto accountRequestDto);
    AccountResponseDto update(@NonNull Long id, @NonNull AccountRequestDto accountRequestDto);
    Account getEntityById(@NonNull Long id, @NonNull Boolean withError);
    AccountResponseDto getResponseById(@NonNull Long id);
    Page<AccountResponseDto> getAll(@NonNull AccountFilter filter, @NonNull Pageable pageable);
    void deleteById(@NonNull Long id);
}
