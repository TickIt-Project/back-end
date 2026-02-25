package com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities;

import com.acme.tickit.tickitbackend.shared.domain.model.entities.AuditableModel;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.Category;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateFieldCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.CategoryField;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.FieldType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Field extends AuditableModel {

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    private String fieldName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FieldType fieldType;

    private Boolean isMandatory;

    private Boolean othersAvailable;

    private Number infNumLimit;
    private Number supNumLimit;

    private Number infWordsLimit;
    private Number supWordsLimit;

    private Number infCharactersLimit;
    private Number supCharactersLimit;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormOption> formOptions = new ArrayList<>();

    public Field() {}

    public Field(CreateFieldCommand command, Category category) {
        this.category = category;
        this.fieldName = command.fieldName();
        this.fieldType = FieldType.valueOf(command.fieldType());
        this.isMandatory = command.isMandatory();
        this.othersAvailable = command.othersAvailable();
        this.infNumLimit = command.infNumLimit() != null ? command.infNumLimit() : null;
        this.supNumLimit = command.supNumLimit() != null ? command.supNumLimit() : null;
        this.infWordsLimit = command.infWordsLimit() != null ? command.infWordsLimit() : null;
        this.supWordsLimit = command.supWordsLimit() != null ? command.supWordsLimit() : null;
        this.infCharactersLimit = command.infCharactersLimit() != null ? command.infCharactersLimit() : null;
        this.supCharactersLimit = command.supCharactersLimit() != null ? command.supCharactersLimit() : null;
    }

    public Field(CategoryField def, Category category) {
        this.category = category;
        this.fieldName = def.fieldName();
        this.fieldType = FieldType.valueOf(def.fieldType());
        this.isMandatory = def.isMandatory();
        this.othersAvailable = def.othersAvailable();
        this.infNumLimit = def.infNumLimit();
        this.supNumLimit = def.supNumLimit();
        this.infWordsLimit = def.infWordsLimit();
        this.supWordsLimit = def.supWordsLimit();
        this.infCharactersLimit = def.infCharactersLimit();
        this.supCharactersLimit = def.supCharactersLimit();
    }

    public void addFormOption(FormOption formOption) {
        this.formOptions.add(formOption);
    }

    public void removeFormOption(FormOption formOption) {
        this.formOptions.remove(formOption);
    }
}
