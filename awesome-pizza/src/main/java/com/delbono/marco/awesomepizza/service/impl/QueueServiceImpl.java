package com.delbono.marco.awesomepizza.service.impl;

import com.delbono.marco.awesomepizza.entity.OrderedPizza;
import com.delbono.marco.awesomepizza.service.QueueService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class QueueServiceImpl implements QueueService {
    private final Queue<OrderedPizza> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void addAllToQueue(Collection<OrderedPizza> itemList) {
        queue.addAll(itemList);
    }

    @Override
    public void addToQueue(OrderedPizza item) {
        queue.add(item);
    }

    @Override
    public OrderedPizza getFromQueue() {
        return queue.poll();
    }

    @Override
    public int getQueueSize() {
        return queue.size();
    }

    @Override
    public void eraseQueue() {
        queue.clear();
    }
}