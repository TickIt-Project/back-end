package com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FieldRepository extends JpaRepository<Field, UUID> {
    Optional<Field> findById(UUID id);
    Boolean existsByCategory_IdAndFieldName(UUID categoryId, String fieldName);
    List<Field> findByCategory_Id(UUID categoryId);
}
