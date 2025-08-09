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

    @Embedded
    private CompanyID companyID;

    private Boolean notify_active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User() {}

    public User(String username, String email, String password, UUID companyID, Boolean notify_active, Role role) {
        this.personalData = new PersonalData(username, email);
        this.password = new Password(password);
        this.companyID = new CompanyID(companyID);
        this.notify_active = notify_active;
        this.role = role;
    }
}
