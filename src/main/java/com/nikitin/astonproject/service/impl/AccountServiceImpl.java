package com.nikitin.astonproject.service.impl;

import com.nikitin.astonproject.dto.filter.AccountFilter;
import com.nikitin.astonproject.dto.request.AccountRequestDto;
import com.nikitin.astonproject.dto.request.CardRequestDto;
import com.nikitin.astonproject.dto.response.AccountResponseDto;
import com.nikitin.astonproject.dto.specification.AccountSpecification;
import com.nikitin.astonproject.entity.Account;
import com.nikitin.astonproject.exception.BadRequestException;
import com.nikitin.astonproject.exception.NotFoundException;
import com.nikitin.astonproject.mapper.AccountMapper;
import com.nikitin.astonproject.repository.AccountRepository;
import com.nikitin.astonproject.service.AccountService;
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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final String ACCOUNT_ALREADY_EXIST_MESSAGE = "Account with name - %s already exist";
    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account with id - %s not found";

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final CardService cardService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AccountResponseDto create(@NonNull AccountRequestDto accountRequestDto) {
        checkAccountForAlreadyExist(accountRequestDto.getName(), null);

        var account = accountRepository.save(accountMapper.toEntity(accountRequestDto));

        autoCreateFirstCard(account.getId());

        return accountMapper.toResponseDto(account);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountResponseDto update(@NonNull Long id, @NonNull AccountRequestDto accountRequestDto) {
        var accountForUpdate = getEntityById(id, true);

        checkAccountForAlreadyExist(accountRequestDto.getName(), accountForUpdate.getName());

        var account = accountMapper.toPatchEntity(accountRequestDto, accountForUpdate);

        return accountMapper.toResponseDto(accountRepository.save(account));
    }

    @Override
    @Transactional(readOnly = true)
    public Account getEntityById(@NonNull Long id, @NonNull Boolean withError) {
        if (withError) {
            return accountRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id)));
        }

        return accountRepository.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDto getResponseById(@NonNull Long id) {
        return accountMapper.toResponseDto(getEntityById(id, true));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponseDto> getAll(@NonNull AccountFilter filter, @NonNull Pageable pageable) {
        return accountRepository.findAll(AccountSpecification.filterBy(filter), pageable)
                .map(accountMapper::toResponseDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteById(@NonNull Long id) {
        var account = getEntityById(id, true);

        accountRepository.delete(account);
    }

    private void checkAccountForAlreadyExist(String nameFromDto, String nameFromEntity) {
        Optional.ofNullable(nameFromDto)
                .flatMap(accountRepository::findByNameIgnoreCase)
                .ifPresent(profile -> {
                    if (!nameFromDto.equals(nameFromEntity)) {
                        throw new BadRequestException(String.format(ACCOUNT_ALREADY_EXIST_MESSAGE, nameFromDto));
                    }
                });
    }

    private void autoCreateFirstCard(Long accountId) {
        cardService.create(CardRequestDto.builder()
                .accountId(accountId)
                .amountBudget(BigDecimal.ZERO)
                .build());
    }
}
