package com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities;

import com.acme.tickit.tickitbackend.shared.domain.model.entities.AuditableModel;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class CompanyRole extends AuditableModel {

    private String name;

    @Embedded
    private CompanyID companyID;

    public CompanyRole() {}

    public CompanyRole(String name, UUID companyID) {
        this.name = name;
        this.companyID = new CompanyID(companyID);
    }
}
