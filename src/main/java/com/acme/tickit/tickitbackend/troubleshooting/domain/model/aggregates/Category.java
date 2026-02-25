package com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates;

import com.acme.tickit.tickitbackend.shared.domain.model.entities.AuditableModel;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateCategoryCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.Field;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category extends AuditableModel {

    @Embedded
    private CompanyID companyId;

    private String name;
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Field> fields = new ArrayList<>();

    public Category() {}

    public Category(CreateCategoryCommand command) {
        this.companyId = new CompanyID(command.companyId());
        this.name = command.name();
        this.description = command.description();
    }

    public void addField(Field field) {
        this.fields.add(field);
    }

    public void removeField(Field field) {
        this.fields.remove(field);
    }
}
