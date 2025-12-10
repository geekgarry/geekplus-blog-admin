package com.geekplus.common.core.async;

import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * author     : geekplus
 * description: 异步任务处理
 */
@Component
public class AsyncProcessor {
    public CompletableFuture processAsync() {
        CompletableFuture future = new CompletableFuture();
        // 模拟异步处理耗时操作
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(27);
                String result = "Async processing completed.";
                future.complete(result);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        }).start();
        return future;
    }
}
