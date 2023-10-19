package com.nikitin.astonproject.mapper;

import com.nikitin.astonproject.dto.request.CardRequestDto;
import com.nikitin.astonproject.dto.response.CardResponseDto;
import com.nikitin.astonproject.entity.Account;
import com.nikitin.astonproject.entity.Card;
import com.nikitin.astonproject.service.AccountService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CardMapper {

    private AccountService accountService;

    @Mapping(source = "dto.accountId", target = "account", qualifiedByName = "converterToEntityAccount")
    public abstract Card toEntity(CardRequestDto dto);

    @Mapping(source = "dto.accountId", target = "account", qualifiedByName = "converterToEntityAccount")
    public abstract Card toPatchEntity(CardRequestDto dto, @MappingTarget Card entityForUpdate);

    public abstract CardResponseDto toResponseDto(Card entity);

    @Named("converterToEntityAccount")
    protected Account converterToEntityAccount(Long id) {
        return accountService.getEntityById(id, true);
    }

    @Autowired
    @Lazy
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
