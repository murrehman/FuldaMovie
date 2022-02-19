package com.bwma.msc.cms.thread.tools;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.LinkedBlockingQueue;

@Getter
@Setter
public class Buffer<T> {
    private LinkedBlockingQueue<T> buffer = new LinkedBlockingQueue<>();
    private boolean producerEnded = false;

    public void enqueueElement(T element){
        this.buffer.add(element);
    }

    public T dequeElement() throws InterruptedException {
       return this.buffer.take();
    }

    public boolean isEmpty(){
        return this.buffer.isEmpty();
    }

}
