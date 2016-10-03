package org.webonise.springboot.waitandnotify;

import org.springframework.stereotype.Component;

@Component
public class SharedItem {
    private int entity;

    public void setEntity(int entity) {
        this.entity = entity;
    }
}
