package com.saleh.cors_esaxon.commonapi.events;

import lombok.Data;
import lombok.Getter;
@Data
public abstract class BaseEvent <T> {
    private T id ;

    protected BaseEvent(T id) {
        this.id = id;
    }
}
