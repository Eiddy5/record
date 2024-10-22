package org.newcaffeina;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadPoolFactory {


    public static ExecutorService buildFixExecutor(int corePoolSize)
    {
        return Executors.newFixedThreadPool(corePoolSize, Thread::new);
    }
}
