package com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities;

import com.acme.tickit.tickitbackend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FormOption extends AuditableModel {

    private String optionName;

    public FormOption() {}

    public FormOption(String optionName) {
        this.optionName = optionName;
    }
}
