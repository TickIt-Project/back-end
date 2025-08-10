package com.acme.tickit.tickitbackend.iam.domain.model.aggregates;

import com.acme.tickit.tickitbackend.iam.domain.model.entities.Role;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Password;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.PersonalData;
import com.acme.tickit.tickitbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

    @Embedded
    private PersonalData personalData;

    @Embedded
    private Password password;

    private Boolean notify_active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public User() {}

    public User(String username, String email, String password, Boolean notify_active, Role role, Company company) {
        this.personalData = new PersonalData(username, email);
        this.password = new Password(password);
        this.notify_active = notify_active;
        this.role = role;
        this.company = company;
    }
}
