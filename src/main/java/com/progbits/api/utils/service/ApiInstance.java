package com.progbits.api.utils.service;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author scarr
 * @param <T> The Type of Class being Instanced
 */
public class ApiInstance<T> {
    private T instance;
    private final ReentrantLock lock;
    public final CountDownLatch configured; 
    
    public ApiInstance() {
        lock = new ReentrantLock();
        configured = new CountDownLatch(1);
    }
    
    public T getInstance(Class<T> clazz) {
        if (instance == null) {
            lock.lock();

            try {
                if (instance == null) {
                    T sample = (T) clazz.getConstructors()[0].newInstance();
                    
                    if (sample instanceof ApiService srv) {
                        srv.configure();
                    }
                    
                    configured.countDown();
                    
                    instance = sample;
                }
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException ix) {
                // Catch Error
            } finally {
                lock.unlock();
            }
        }

        try {
            configured.await();
        } catch (InterruptedException ex) {
            // nothing to report
        }

        return instance;
    }
}
