package com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities;

import com.acme.tickit.tickitbackend.shared.domain.model.entities.AuditableModel;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.Category;
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
}
