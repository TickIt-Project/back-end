package com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findById(UUID id);
    Boolean existsByCompanyIdAndName(CompanyID companyId, String name);
    List<Category> findAllByCompanyId(CompanyID companyId);
}
