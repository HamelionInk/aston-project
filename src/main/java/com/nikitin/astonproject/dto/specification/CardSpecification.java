package com.nikitin.astonproject.dto.specification;

import com.nikitin.astonproject.dto.filter.CardFilter;
import com.nikitin.astonproject.entity.Account_;
import com.nikitin.astonproject.entity.Card;
import com.nikitin.astonproject.entity.Card_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class CardSpecification {

    public Specification<Card> filterBy(CardFilter filter) {
        Specification<Card> specification = (root, query, criteriaBuilder) -> null;

        if (!CollectionUtils.isEmpty(filter.getIds())) {
            specification = Optional.of(specification.and(inIds(filter.getIds())))
                    .orElse(specification);
        }

        if (!CollectionUtils.isEmpty(filter.getAccountIds())) {
            specification = Optional.of(specification.and(inAccountIds(filter.getAccountIds())))
                    .orElse(specification);
        }

        if (!CollectionUtils.isEmpty(filter.getNumberCards())) {
            specification = Optional.of(specification.and(inNumberCards(filter.getNumberCards())))
                    .orElse(specification);
        }

        if (Objects.nonNull(filter.getGreaterDateCreate())) {
            specification = Optional.of(specification.and(equalsOrGreaterDateCreate(filter.getGreaterDateCreate())))
                    .orElse(specification);
        }

        if (Objects.nonNull(filter.getLessDateCreate())) {
            specification = Optional.of(specification.and(equalsOrLessDateCreate(filter.getLessDateCreate())))
                    .orElse(specification);
        }

        return specification;
    }

    private static Specification<Card> inIds(List<Long> ids) {
        return (root, query, criteriaBuilder) ->
                root.get(Card_.ID).in(ids.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

    private static Specification<Card> inAccountIds(List<Long> accountIds) {
        return (root, query, criteriaBuilder) ->
                root.get(Card_.ACCOUNT).get(Account_.ID).in(accountIds.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

    private static Specification<Card> inNumberCards(List<Long> numberCards) {
        return (root, query, criteriaBuilder) ->
                root.get(Card_.NUMBER_CARD).in(numberCards.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

    private static Specification<Card> equalsOrGreaterDateCreate(Instant greaterDateCreate) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(Card_.DATE_CREATE), greaterDateCreate));
    }

    private static Specification<Card> equalsOrLessDateCreate(Instant lessDateCreate) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(Card_.DATE_CREATE), lessDateCreate));
    }
}
