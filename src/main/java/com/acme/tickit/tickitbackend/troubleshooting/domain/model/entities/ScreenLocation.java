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
public class ScreenLocation extends AuditableModel {

    private String name;
    private String url;

    @Embedded
    private CompanyID companyID;

    public ScreenLocation() {}

    public ScreenLocation(String name, String url, UUID companyID) {
        this.name = name;
        this.url = url;
        this.companyID = new CompanyID(companyID);
    }
}
