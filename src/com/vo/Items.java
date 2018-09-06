package com.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by phant0ms on 2018/7/26.
 */
@Entity
public class Items {
    private int id;
    private String pname;
    private Double pcost;
    private Double pdenomination;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pname", nullable = true, length = 255)
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Basic
    @Column(name = "pcost", nullable = true, precision = 0)
    public Double getPcost() {
        return pcost;
    }

    public void setPcost(Double pcost) {
        this.pcost = pcost;
    }

    @Basic
    @Column(name = "pdenomination", nullable = true, precision = 0)
    public Double getPdenomination() {
        return pdenomination;
    }

    public void setPdenomination(Double pdenomination) {
        this.pdenomination = pdenomination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Items items = (Items) o;

        if (id != items.id) return false;
        if (pname != null ? !pname.equals(items.pname) : items.pname != null) return false;
        if (pcost != null ? !pcost.equals(items.pcost) : items.pcost != null) return false;
        if (pdenomination != null ? !pdenomination.equals(items.pdenomination) : items.pdenomination != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pname != null ? pname.hashCode() : 0);
        result = 31 * result + (pcost != null ? pcost.hashCode() : 0);
        result = 31 * result + (pdenomination != null ? pdenomination.hashCode() : 0);
        return result;
    }
}
