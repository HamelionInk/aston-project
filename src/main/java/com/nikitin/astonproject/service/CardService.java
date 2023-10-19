package com.nikitin.astonproject.service;

import com.nikitin.astonproject.dto.filter.CardFilter;
import com.nikitin.astonproject.dto.request.CardRequestDto;
import com.nikitin.astonproject.dto.response.CardResponseDto;
import com.nikitin.astonproject.entity.Card;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {

    CardResponseDto create(@NonNull CardRequestDto cardRequestDto);
    CardResponseDto update(@NonNull Long id, @NonNull CardRequestDto cardRequestDto);
    Card getEntityById(@NonNull Long id, @NonNull Boolean withError);
    CardResponseDto getResponseById(@NonNull Long id);
    Card getEntityByNumberCard(@NonNull Long numberCard, @NonNull Boolean withError);
    CardResponseDto getResponseByNumberCard(@NonNull Long numberCard);
    Page<CardResponseDto> getAll(@NonNull CardFilter filter, @NonNull Pageable pageable);
    void deleteById(@NonNull Long id);
}
