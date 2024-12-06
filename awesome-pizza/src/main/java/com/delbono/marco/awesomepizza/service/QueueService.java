package com.delbono.marco.awesomepizza.service;

import com.delbono.marco.awesomepizza.entity.OrderedPizza;

import java.util.Collection;
import java.util.List;

public interface QueueService {
    void addAllToQueue(Collection<OrderedPizza> itemList);

    void addToQueue(OrderedPizza item);

    OrderedPizza getFromQueue();

    int getQueueSize();

    void eraseQueue();
}
