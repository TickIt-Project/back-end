package com.acme.tickit.tickitbackend.iam.domain.model.entities;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Roles;
import com.acme.tickit.tickitbackend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Role extends AuditableModel {
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Roles name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public Role() {}

    public Role(Roles name) {
        this.name = name;
    }
}
