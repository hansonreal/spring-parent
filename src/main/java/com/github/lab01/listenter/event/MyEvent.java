package com.github.lab01.listenter.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author: hsr
 * @desc: 自定义事件
 * @since: 2024-11-08 16:56
 */
public class MyEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent} with its {@link #getTimestamp() timestamp}
     * set to {@link System#currentTimeMillis()}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     * @see #ApplicationEvent(Object, Clock)
     */
    public MyEvent(Object source) {
        super(source);
    }
}
