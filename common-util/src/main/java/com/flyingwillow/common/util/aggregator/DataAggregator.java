package com.flyingwillow.common.util.aggregator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

@Slf4j
public class DataAggregator<T> {

    private BatchQueue<T> queue;
    private Thread thread;
    private AtomicBoolean started = new AtomicBoolean(false);

    public DataAggregator(int size, long maxWaitTime, Consumer<List<T>> consumer) {
        this.queue = new BatchQueue<>(size);
        this.thread = new Thread(() -> {
            log.debug("Consumer running....");
            while (started.get()) {
                log.debug("Getting data...");
                List<T> list = queue.take(maxWaitTime);
                consumer.accept(list);
            }
            log.debug("Consumer ending....");
        });
    }

    public void put(T data) {
        queue.put(data);
    }

    public void start() {
        if (started.compareAndSet(false, true)) {
            thread.start();
            log.debug("Consumer started!");
        }
    }

    public void shutdown() {
        if (started.compareAndSet(true, false)) {
            thread = null;
            queue = null;
            log.debug("Consumer shutdown");
        }
    }

    public class BatchQueue<T> {
        private List<T> queue;
        private volatile CountDownLatch countDownLatch;
        private ReentrantLock lock = new ReentrantLock();
        private Condition fullLock = lock.newCondition();
        private int size;

        BatchQueue(int size) {
            this.size = size;
            this.queue = new ArrayList<>(size);
            this.countDownLatch = new CountDownLatch(size);
        }

        void put(T data) {
            try {
                this.lock.lock();
                while (queue.size() == size) {
                    log.info("Queue is full");
                    fullLock.await();
                }
                queue.add(data);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                //
                Thread.currentThread().interrupt();
                log.debug("Interrupted!");
            } finally {
                this.lock.unlock();
            }
        }

        List<T> take(long time) {
            List<T> list;
            try {
                countDownLatch.await(time, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                //
                Thread.currentThread().interrupt();
                log.debug("Interrupted!");
            } finally {
                list = getList();
            }
            return list;
        }

        private void reset() {
            queue.clear();
            countDownLatch = new CountDownLatch(size);
            fullLock.signalAll();
        }

        private List<T> getList() {
            try {
                this.lock.lock();
                if (CollectionUtils.isEmpty(queue)) {
                    return Collections.emptyList();
                } else {
                    List<T> dest = new ArrayList<>(size);
                    dest.addAll(queue);
                    reset();
                    return dest;
                }
            } finally {
                this.lock.unlock();
            }
        }
    }
}
