package org.example.model.ingame.map.resourses;

import java.io.Serializable;
import java.util.Objects;

public class Resource implements Serializable {
    private final Resources resourceName;
    private int amount;

    public Resource(Resources resource, int amount) {
        this.resourceName = resource;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return resourceName.equals(resource.resourceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceName);
    }

    public Resources getResourceName() {
        return resourceName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int count) {
        amount = count;
    }

    public void changeAmount(int amount) {
        this.amount += amount;
    }
}
