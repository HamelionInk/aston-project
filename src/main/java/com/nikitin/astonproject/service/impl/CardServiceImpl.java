package com.nikitin.astonproject.service.impl;

import com.nikitin.astonproject.dto.filter.CardFilter;
import com.nikitin.astonproject.dto.request.CardRequestDto;
import com.nikitin.astonproject.dto.response.CardResponseDto;
import com.nikitin.astonproject.dto.specification.CardSpecification;
import com.nikitin.astonproject.entity.Card;
import com.nikitin.astonproject.exception.NotFoundException;
import com.nikitin.astonproject.mapper.CardMapper;
import com.nikitin.astonproject.repository.CardRepository;
import com.nikitin.astonproject.service.CardService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private static final String CARD_NOT_FOUND_MESSAGE = "Card with %s - %s not found";

    private final CardMapper cardMapper;
    private final CardRepository cardRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CardResponseDto create(@NonNull CardRequestDto cardRequestDto) {
        var card = cardMapper.toEntity(cardRequestDto);
        card.setNumberCard(generateNumberCard(card.getDateCreate()));

        return cardMapper.toResponseDto(cardRepository.save(card));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CardResponseDto update(@NonNull Long id, @NonNull CardRequestDto cardRequestDto) {
        var cardForUpdate = getEntityById(id, true);

        var card = cardMapper.toPatchEntity(cardRequestDto, cardForUpdate);

        return cardMapper.toResponseDto(card);
    }

    @Override
    @Transactional(readOnly = true)
    public Card getEntityById(@NonNull Long id, @NonNull Boolean withError) {
        if (withError) {
            return cardRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format(CARD_NOT_FOUND_MESSAGE, "id", id)));
        }

        return cardRepository.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponseDto getResponseById(@NonNull Long id) {
        return cardMapper.toResponseDto(getEntityById(id, true));
    }

    @Override
    @Transactional(readOnly = true)
    public Card getEntityByNumberCard(@NonNull Long numberCard, @NonNull Boolean withError) {
        if (withError) {
            return cardRepository.findByNumberCard(numberCard)
                    .orElseThrow(() -> new NotFoundException(String.format(CARD_NOT_FOUND_MESSAGE, "numberCard", numberCard)));
        }

        return cardRepository.findByNumberCard(numberCard)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponseDto getResponseByNumberCard(@NonNull Long numberCard) {
        return cardMapper.toResponseDto(getEntityByNumberCard(numberCard, true));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CardResponseDto> getAll(@NonNull CardFilter filter, @NonNull Pageable pageable) {
        return cardRepository.findAll(CardSpecification.filterBy(filter), pageable)
                .map(cardMapper::toResponseDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteById(@NonNull Long id) {
        var card = getEntityById(id, true);

        cardRepository.delete(card);
    }

    private Long generateNumberCard(Instant date) {
        var startIndex = 0;
        var endIndex = 16;

        var generatedNumberCard = Long.valueOf(Long.reverseBytes(date.getEpochSecond())).toString().substring(startIndex, endIndex);

        return toPositive(Long.parseLong(generatedNumberCard));
    }

    private Long toPositive(Long value) {
        return Math.abs(-value);
    }
}
