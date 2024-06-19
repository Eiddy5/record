package org.ignite.listener;

import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.Event;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgnitePredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalMemoryListener implements IgnitePredicate<Event> {
    private static final Logger logger = LoggerFactory.getLogger(GlobalMemoryListener.class);

    @Override
    public boolean apply(Event event) {
        if (event instanceof CacheEvent) {
            CacheEvent cacheEvent = (CacheEvent) event;
            if (event.type() == EventType.EVT_CACHE_OBJECT_PUT || event.type() == EventType.EVT_CACHE_OBJECT_REMOVED) {
                switch (event.type()) {
                    case EventType.EVT_CACHE_OBJECT_EXPIRED : {
                        logger.info("Cache event:{} ----> {} is expired!", cacheEvent.name(), cacheEvent.oldValue());
                        break;
                    }
                    case EventType.EVT_CACHE_OBJECT_PUT : {
                        logger.info("Cache event:{} ----> {} is put!", cacheEvent.name(), cacheEvent.newValue());
                        break;
                    }
                    case EventType.EVT_CACHE_OBJECT_REMOVED : {
                        logger.info("Cache event:{} ----> {} is removed!", cacheEvent.name(), cacheEvent.oldValue());
                        break;
                    }
                }
            }
        }
        return true;
    }
}
