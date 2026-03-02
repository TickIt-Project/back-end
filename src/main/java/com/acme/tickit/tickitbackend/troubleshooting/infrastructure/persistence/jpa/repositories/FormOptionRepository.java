package com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.FormOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormOptionRepository extends JpaRepository<FormOption, UUID> {
    Optional<FormOption> findById(UUID id);
    Boolean existsByField_IdAndOptionName(UUID fieldId, String optionName);
    List<FormOption> findByField_Id(UUID fieldId);
}
