package com.nikitin.astonproject.aspect;

import com.nikitin.astonproject.entity.Account;
import com.nikitin.astonproject.exception.BadRequestException;
import com.nikitin.astonproject.service.CardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Getter
@Aspect
@Component
@RequiredArgsConstructor
public class SecurePinCodeAspect {

    private static final String PIN_CODE_BAD_REQUEST_MESSAGE = "Not valid PIN";
    private static final String PIN_CODE_FIELD_NAME = "pinCode";
    private static final String NUMBER_CARD_FIELD_NAME = "numberCard";

    private final CardService cardService;

    @Before("@annotation(com.nikitin.astonproject.aspect.annotation.SecurePinCode)")
    public void pinCodeAuthentication(JoinPoint joinPoint) {
        var signatureMethod = (MethodSignature) joinPoint.getSignature();
        var method = signatureMethod.getMethod();

        var secureData = Arrays.stream(method.getParameterTypes())
                .filter(filter -> {
                    try {
                        filter.getDeclaredField(PIN_CODE_FIELD_NAME);
                        filter.getDeclaredField(NUMBER_CARD_FIELD_NAME);
                        return true;
                    } catch (NoSuchFieldException e) {
                        return false;
                    }
                }).toList();

        if (!CollectionUtils.isEmpty(secureData)) {
            secureData.forEach(type -> Arrays.stream(joinPoint.getArgs()).forEach(arg -> {
                if (type.isInstance(arg)) {
                    var obj = type.cast(arg);
                    try {
                        var pinCodeField = obj.getClass().getDeclaredField(PIN_CODE_FIELD_NAME);
                        pinCodeField.setAccessible(true);

                        var numberCardField = obj.getClass().getDeclaredField(NUMBER_CARD_FIELD_NAME);
                        numberCardField.setAccessible(true);

                        var pinCode = (Integer) pinCodeField.get(obj);
                        var numberCard = (Long) numberCardField.get(obj);

                        var card = cardService.getEntityByNumberCard(numberCard, true);

                        Optional.ofNullable(card.getAccount())
                                .map(Account::getPinCode)
                                .ifPresent(accountPinCode -> {
                                    if (!Objects.equals(accountPinCode, pinCode)) {
                                        throw new BadRequestException(PIN_CODE_BAD_REQUEST_MESSAGE);

                                    }
                                });
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new BadRequestException(PIN_CODE_BAD_REQUEST_MESSAGE);
                    }
                }
            }));
        } else {
            throw new BadRequestException(PIN_CODE_BAD_REQUEST_MESSAGE);
        }
    }
}
