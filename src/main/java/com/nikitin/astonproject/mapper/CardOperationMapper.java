package com.nikitin.astonproject.mapper;

import com.nikitin.astonproject.dto.request.CardOperationRequestDto;
import com.nikitin.astonproject.dto.response.CardOperationResponseDto;
import com.nikitin.astonproject.entity.Card;
import com.nikitin.astonproject.entity.CardOperation;
import com.nikitin.astonproject.service.CardService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CardOperationMapper {

    private CardService cardService;

    @Mapping(source = "dto.cardId", target = "card", qualifiedByName = "converterToEntityCard")
    public abstract CardOperation toEntity(CardOperationRequestDto dto);

    public abstract CardOperation toPatchEntity(CardOperationRequestDto dto, @MappingTarget CardOperation entityForUpdate);

    public abstract CardOperationResponseDto toResponseDto(CardOperation entity);

    @Named("converterToEntityCard")
    protected Card converterToEntityCard(Long cardId) {
        return cardService.getEntityById(cardId, true);
    }
    @Autowired
    @Lazy
    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }
}
