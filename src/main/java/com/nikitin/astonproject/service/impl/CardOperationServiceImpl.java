package com.nikitin.astonproject.service.impl;

import com.nikitin.astonproject.dto.filter.CardOperationFilter;
import com.nikitin.astonproject.dto.request.CardOperationDepositRequestDto;
import com.nikitin.astonproject.dto.request.CardOperationRequestDto;
import com.nikitin.astonproject.dto.request.CardOperationTransferRequestDto;
import com.nikitin.astonproject.dto.request.CardOperationWithdrawRequestDto;
import com.nikitin.astonproject.dto.request.CardRequestDto;
import com.nikitin.astonproject.dto.response.CardOperationResponseDto;
import com.nikitin.astonproject.dto.specification.CardOperationSpecification;
import com.nikitin.astonproject.entity.CardOperation;
import com.nikitin.astonproject.exception.BadRequestException;
import com.nikitin.astonproject.exception.NotFoundException;
import com.nikitin.astonproject.mapper.CardOperationMapper;
import com.nikitin.astonproject.repository.CardOperationRepository;
import com.nikitin.astonproject.service.CardOperationService;
import com.nikitin.astonproject.service.CardService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardOperationServiceImpl implements CardOperationService {

    private static final String CARD_OPERATION_BAD_REQUEST_MESSAGE = "Not enough money on card - %s";
    private static final String CARD_OPERATION_NOT_FOUND_MESSAGE = "Card operation with %s - %s not found";

    private final CardOperationMapper cardOperationMapper;
    private final CardOperationRepository cardOperationRepository;
    private final CardService cardService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CardOperationResponseDto create(@NonNull CardOperationRequestDto cardOperationRequestDto) {
        var cardOperation = cardOperationMapper.toEntity(cardOperationRequestDto);

        return cardOperationMapper.toResponseDto(cardOperationRepository.save(cardOperation));
    }

    @Override
    @Transactional(readOnly = true)
    public CardOperation getEntityById(@NonNull Long id, @NonNull Boolean withError) {
        if (withError) {
            return cardOperationRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format(CARD_OPERATION_NOT_FOUND_MESSAGE, "id", id)));
        }

        return cardOperationRepository.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public CardOperationResponseDto getResponseById(@NonNull Long id) {
        return cardOperationMapper.toResponseDto(getEntityById(id, true));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CardOperationResponseDto> getAll(@NonNull CardOperationFilter filter, @NonNull Pageable pageable) {
        return cardOperationRepository.findAll(CardOperationSpecification.filterBy(filter), pageable)
                .map(cardOperationMapper::toResponseDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CardOperationResponseDto depositByNumberCard(@NonNull CardOperationDepositRequestDto cardOperationDepositRequestDto) {
        var card = cardService.getEntityByNumberCard(cardOperationDepositRequestDto.getNumberCard(), true);

        var updateBalance = card.getAmountBudget().add(cardOperationDepositRequestDto.getAmount());

        var cardAfterUpdate = cardService.update(card.getId(), CardRequestDto.builder()
                .amountBudget(updateBalance)
                .build());

        return create(CardOperationRequestDto.builder()
                .cardId(cardAfterUpdate.getId())
                .operationType(cardOperationDepositRequestDto.getOperationType())
                .amountOperation(cardOperationDepositRequestDto.getAmount())
                .dateOperation(Instant.now())
                .build());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CardOperationResponseDto withdrawByNumberCard(@NonNull CardOperationWithdrawRequestDto cardOperationWithdrawRequestDto) {
        var card = cardService.getEntityByNumberCard(cardOperationWithdrawRequestDto.getNumberCard(), true);

        var updateBalance = card.getAmountBudget().subtract(cardOperationWithdrawRequestDto.getAmount());

        if (0 > updateBalance.compareTo(BigDecimal.ZERO)) {
            throw new BadRequestException(String.format(CARD_OPERATION_BAD_REQUEST_MESSAGE, cardOperationWithdrawRequestDto.getNumberCard()));
        }

        var cardAfterUpdate = cardService.update(card.getId(), CardRequestDto.builder()
                .amountBudget(updateBalance)
                .build());

        return create(CardOperationRequestDto.builder()
                .cardId(cardAfterUpdate.getId())
                .operationType(cardOperationWithdrawRequestDto.getOperationType())
                .amountOperation(cardOperationWithdrawRequestDto.getAmount())
                .dateOperation(Instant.now())
                .build());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CardOperationResponseDto transferByNumberCard(@NonNull CardOperationTransferRequestDto cardOperationTransferRequestDto) {
        var cardOperationResponseDto = withdrawByNumberCard(CardOperationWithdrawRequestDto.builder()
                .numberCard(cardOperationTransferRequestDto.getProducerNumberCard())
                .operationType(cardOperationTransferRequestDto.getOperationType())
                .amount(cardOperationTransferRequestDto.getAmount())
                .pinCode(cardOperationTransferRequestDto.getPinCode())
                .build());

        depositByNumberCard(CardOperationDepositRequestDto.builder()
                .numberCard(cardOperationTransferRequestDto.getConsumerNumberCard())
                .operationType(cardOperationTransferRequestDto.getOperationType())
                .amount(cardOperationTransferRequestDto.getAmount())
                .build());

        return cardOperationResponseDto;
    }
}
