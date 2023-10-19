package com.nikitin.astonproject.mapper;

import com.nikitin.astonproject.dto.filter.CardFilter;
import com.nikitin.astonproject.dto.request.AccountRequestDto;
import com.nikitin.astonproject.dto.response.AccountResponseDto;
import com.nikitin.astonproject.dto.response.CardResponseDto;
import com.nikitin.astonproject.entity.Account;
import com.nikitin.astonproject.service.CardService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class AccountMapper {

    private CardService cardService;

    public abstract Account toEntity(AccountRequestDto dto);

    public abstract Account toPatchEntity(AccountRequestDto dto, @MappingTarget Account entityForUpdate);

    @Mapping(source = "entity.id", target = "cards", qualifiedByName = "converterToResponseDtoAccount")
    public abstract AccountResponseDto toResponseDto(Account entity);

    @Named("converterToResponseDtoAccount")
    protected List<CardResponseDto> converterToResponseDtoAccount(Long id) {
        var cards = cardService.getAll(CardFilter.builder()
                .accountIds(List.of(id))
                .build(), Pageable.unpaged());

        return cards.getContent();
    }

    @Autowired
    @Lazy
    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }
}
