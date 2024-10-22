package org.newcaffeina.watch;

import com.github.benmanes.caffeine.cache.Cache;
import org.newcaffeina.message.Message;
import org.redisson.api.RTopic;
import redisson.Redisson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Watcher {

    private final String TOPIC = "cache_watcher";
    private final int THREAD_POOL_SIZE = 10;
    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    private final RTopic topic = Redisson.getTopic(TOPIC);
    private Message message;
    private Cache cache;

    public Watcher() {

    }


    public void watch(){
        topic.addListener(Message.class, (charSequence, message) -> {
            switch (message.getAction()){
                case ADD -> executorService.submit(this::processAdd);
                case REMOVE -> executorService.submit(this::processRemove);
                case UPDATE -> executorService.submit(this::processUpdate);
                case REPLACE -> executorService.submit(this::processReplace);
                case REPLACE_ALL -> executorService.submit(this::processReplaceAll);
            }
        });
    }

    public void send(Message message){
        topic.publish(message);
    }

    protected void processAdd(){
        // todo add
        System.out.println("add");
    }
    protected void processRemove(){
        // todo remove
        System.out.println("remove");
    }
    protected void processUpdate(){
        // todo update
        System.out.println("update");
    }
    protected void processClear(){
        // todo clear
        System.out.println("clear");
    }

    protected void processReplace(){
        // todo replace
        System.out.println("replace");
    }

    protected void processReplaceAll(){
        // todo replaceAll
        System.out.println("replace all");
    }

}
