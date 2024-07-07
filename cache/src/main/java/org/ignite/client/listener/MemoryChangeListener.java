package org.ignite.client.listener;

import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.Event;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgnitePredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoryChangeListener implements IgnitePredicate<Event> {
    private static final Logger logger = LoggerFactory.getLogger(MemoryChangeListener.class);

    @Override
    public boolean apply(Event event) {
        if (event instanceof CacheEvent) {
            CacheEvent cacheEvent = (CacheEvent) event;
            switch (event.type()) {
                case EventType.EVT_CACHE_OBJECT_EXPIRED: {
                    logger.info("\uD83C\uDF29 \uD83C\uDF29 \uD83C\uDF29 {} is expired!", cacheEvent.oldValue());
                    break;
                }
                case EventType.EVT_CACHE_OBJECT_PUT: {
                    if (cacheEvent.hasOldValue()) {
                        // 更新
                        logger.info("\uD83C\uDF29 \uD83C\uDF29 \uD83C\uDF29 update data {}", cacheEvent.newValue());
                    }else {
                        // 新增
                        logger.info("\uD83C\uDF29 \uD83C\uDF29 \uD83C\uDF29 add data {}", cacheEvent.newValue());
                    }
                    break;
                }
                case EventType.EVT_CACHE_OBJECT_REMOVED: {
                    logger.info("\uD83C\uDF29 \uD83C\uDF29 \uD83C\uDF29 remove data {}", cacheEvent.oldValue());
                    break;
                }
            }
        }
        return true;
    }
}
