package com.BootCampProject1.BootCampProject1.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ROLE {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

  //  @ManyToMany
//    private Set<USER> user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
