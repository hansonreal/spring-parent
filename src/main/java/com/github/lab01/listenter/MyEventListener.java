package com.github.lab01.listenter;

import com.github.lab01.listenter.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author: hsr
 * @desc: 事件监听器
 * @since: 2024-11-08 16:57
 */
@Slf4j
@Component
public class MyEventListener implements ApplicationListener<MyEvent> {
    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info("使用实现 ApplicationListener 接口 收到事件：{}", event);
    }



    @EventListener
    public void listener(MyEvent myEvent){
        log.info("使用实现 @EventListener 接口 收到事件：{}", myEvent);
    }
}
