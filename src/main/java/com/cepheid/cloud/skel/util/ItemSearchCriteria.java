package com.cepheid.cloud.skel.util;


import com.cepheid.cloud.skel.constant.State;

import java.io.Serializable;

public class ItemSearchCriteria implements Serializable {
    private String name;
    private Long id;
    private State status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getStatus() {
        return status;
    }

    public void setStatus(State status) {
        this.status = status;
    }
}
