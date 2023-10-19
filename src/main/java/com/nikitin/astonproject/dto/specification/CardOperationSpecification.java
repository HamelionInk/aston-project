package com.nikitin.astonproject.dto.specification;

import com.nikitin.astonproject.dto.filter.CardOperationFilter;
import com.nikitin.astonproject.entity.CardOperation;
import com.nikitin.astonproject.entity.CardOperation_;
import com.nikitin.astonproject.entity.Card_;
import com.nikitin.astonproject.entity.enums.OperationType;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class CardOperationSpecification {

    public Specification<CardOperation> filterBy(CardOperationFilter filter) {
        Specification<CardOperation> specification = (root, query, criteriaBuilder) -> null;

        if (!CollectionUtils.isEmpty(filter.getIds())) {
            specification = Optional.of(specification.and(inIds(filter.getIds())))
                    .orElse(specification);
        }

        if (!CollectionUtils.isEmpty(filter.getCardIds())) {
            specification = Optional.of(specification.and(inCardIds(filter.getCardIds())))
                    .orElse(specification);
        }

        if (Objects.nonNull(filter.getGreaterAmountOperation())) {
            specification = Optional.of(specification.and(equalsOrGreaterAmountOperation(filter.getGreaterAmountOperation())))
                    .orElse(specification);
        }

        if (Objects.nonNull(filter.getLessAmountOperation())) {
            specification = Optional.of(specification.and(equalsOrLessAmountOperation(filter.getLessAmountOperation())))
                    .orElse(specification);
        }

        if (Objects.nonNull(filter.getOperationType())) {
            specification = Optional.of(specification.and(equalsOperationType(filter.getOperationType())))
                    .orElse(specification);
        }

        return specification;
    }

    private static Specification<CardOperation> inIds(List<Long> ids) {
        return (root, query, criteriaBuilder) ->
                root.get(CardOperation_.ID).in(ids.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

    private static Specification<CardOperation> inCardIds(List<Long> cardIds) {
        return (root, query, criteriaBuilder) ->
                root.get(CardOperation_.CARD).get(Card_.ID).in(cardIds.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

    private static Specification<CardOperation> equalsOrGreaterAmountOperation(BigDecimal greaterAmountOperation) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(CardOperation_.AMOUNT_OPERATION), greaterAmountOperation);
    }

    private static Specification<CardOperation> equalsOrLessAmountOperation(BigDecimal lessAmountOperation) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(CardOperation_.AMOUNT_OPERATION), lessAmountOperation);
    }

    private static Specification<CardOperation> equalsOperationType(OperationType operationType) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(CardOperation_.OPERATION_TYPE), operationType);
    }
}
