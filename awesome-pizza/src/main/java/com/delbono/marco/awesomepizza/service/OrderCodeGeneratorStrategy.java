package com.delbono.marco.awesomepizza.service;

public interface OrderCodeGeneratorStrategy {
    String generateOrderCode(Long orderId);
}
