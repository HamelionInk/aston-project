package com.nikitin.astonproject.repository;

import com.nikitin.astonproject.entity.CardOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CardOperationRepository extends JpaRepository<CardOperation, Long>, JpaSpecificationExecutor<CardOperation> {
}
