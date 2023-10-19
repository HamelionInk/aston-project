package com.nikitin.astonproject.entity;

import com.nikitin.astonproject.entity.enums.OperationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operation")
public class CardOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_operation", nullable = false, columnDefinition = "NUMERIC(100000, 10)")
    private BigDecimal amountOperation;

    @Column(name = "date_operation", nullable = false, columnDefinition = "timestamp")
    private Instant dateOperation;

    @Column(name = "operation_type", nullable = false, columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false, columnDefinition = "int8")
    private Card card;
}
