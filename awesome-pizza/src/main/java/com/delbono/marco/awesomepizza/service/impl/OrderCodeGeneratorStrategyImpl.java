package com.delbono.marco.awesomepizza.service.impl;

import com.delbono.marco.awesomepizza.service.OrderCodeGeneratorStrategy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class OrderCodeGeneratorStrategyImpl implements OrderCodeGeneratorStrategy {
    private static final String PATTERN_FORMAT = "yyyyMMddHHmmss";
    private static final String DEFAULT_PREFIX = "ASPIZZA";

    @Getter
    @Value("${order.code.prefix:" + DEFAULT_PREFIX + "}")
    private String prefix;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
            .withZone(ZoneId.systemDefault());

    @Override
    public String generateOrderCode(Long orderId) {
        Instant now = Instant.now();
        return prefix + "-" + formatter.format(now) + "-" + orderId;
    }

    public String getFormat() {
        return PATTERN_FORMAT;
    }
}
