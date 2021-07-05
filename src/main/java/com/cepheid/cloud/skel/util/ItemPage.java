package com.cepheid.cloud.skel.util;

import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class ItemPage implements Serializable {
    private Integer page=0;
    private Integer size=10;
    private Sort.Direction direction= Sort.Direction.ASC;
    private String sortBy="id";

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
