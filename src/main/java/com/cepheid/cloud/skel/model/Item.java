package com.cepheid.cloud.skel.model;

import com.cepheid.cloud.skel.constant.State;
import javassist.runtime.Desc;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;


@Entity
public class Item extends AbstractEntity {
    @NotBlank(message = "Name must not be blank/null!")
    private String name;
    private State state=State.UNDEFINED;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Collection<Description> descriptions=new ArrayList<>();

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Collection<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Collection<Description> descriptions) {
        this.descriptions = descriptions;
    }
}
