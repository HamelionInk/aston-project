package com.nikitin.astonproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_card", nullable = false, unique = true, columnDefinition = "int8")
    private Long numberCard;

    @Column(name = "amount_budget", nullable = false, columnDefinition = "NUMERIC(100000, 10)")
    @Builder.Default
    private BigDecimal amountBudget = BigDecimal.ZERO;

    @Column(name = "date_create", nullable = false, columnDefinition = "timestamp")
    @Builder.Default
    private Instant dateCreate = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id", nullable = false, columnDefinition = "int8")
    private Account account;
}
