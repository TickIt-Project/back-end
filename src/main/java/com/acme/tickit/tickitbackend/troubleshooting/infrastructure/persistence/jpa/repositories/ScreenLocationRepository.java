package com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.ScreenLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScreenLocationRepository extends JpaRepository<ScreenLocation, UUID> {
    Optional<ScreenLocation> findById(UUID id);
    List<ScreenLocation> findAllByCompanyId(CompanyID companyId);
    boolean existsByNameAndCompanyId(String name, CompanyID companyId);
    boolean existsByUrlAndCompanyId(String url, CompanyID companyId);
    boolean existsById(UUID id);
}
