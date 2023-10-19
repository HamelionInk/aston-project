package com.nikitin.astonproject.dto.specification;

import com.nikitin.astonproject.dto.filter.AccountFilter;
import com.nikitin.astonproject.entity.Account;
import com.nikitin.astonproject.entity.Account_;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class AccountSpecification {

    public Specification<Account> filterBy(AccountFilter filter) {
        Specification<Account> specification = (root, query, criteriaBuilder) -> null;

        if (!CollectionUtils.isEmpty(filter.getIds())) {
            specification = Optional.of(specification.and(inIds(filter.getIds())))
                    .orElse(specification);
        }

        if (!CollectionUtils.isEmpty(filter.getNames())) {
            specification = Optional.of(specification.and(likeNames(filter.getNames())))
                    .orElse(specification);
        }

        return specification;
    }

    private static Specification<Account> inIds(List<Long> ids) {
        return (root, query, criteriaBuilder) ->
                root.get(Account_.ID).in(ids.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

    private static Specification<Account> likeNames(List<String> names) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(names.stream()
                        .map(name -> criteriaBuilder.like(criteriaBuilder.upper(root.get(Account_.NAME)),
                                "%" + name.toUpperCase() + "%")).toArray(Predicate[]::new));
    }
}
