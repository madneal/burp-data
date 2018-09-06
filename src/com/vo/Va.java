package com.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Va {
    private int id;
    private String vaname;
    private String vadesc;
    private Integer vacost;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "vaname", nullable = true, length = 255)
    public String getVaname() {
        return vaname;
    }

    public void setVaname(String vaname) {
        this.vaname = vaname;
    }

    @Basic
    @Column(name = "vadesc", nullable = true, length = 255)
    public String getVadesc() {
        return vadesc;
    }

    public void setVadesc(String vadesc) {
        this.vadesc = vadesc;
    }

    @Basic
    @Column(name = "vacost", nullable = true)
    public Integer getVacost() {
        return vacost;
    }

    public void setVacost(Integer vacost) {
        this.vacost = vacost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Va va = (Va) o;

        if (id != va.id) return false;
        if (vaname != null ? !vaname.equals(va.vaname) : va.vaname != null) return false;
        if (vadesc != null ? !vadesc.equals(va.vadesc) : va.vadesc != null) return false;
        if (vacost != null ? !vacost.equals(va.vacost) : va.vacost != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (vaname != null ? vaname.hashCode() : 0);
        result = 31 * result + (vadesc != null ? vadesc.hashCode() : 0);
        result = 31 * result + (vacost != null ? vacost.hashCode() : 0);
        return result;
    }
}
