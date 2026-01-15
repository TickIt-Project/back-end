package com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRoleRepository extends JpaRepository<CompanyRole, UUID> {
    Optional<CompanyRole> findById(UUID id);
    List<CompanyRole> findAllByCompanyId(CompanyID companyId);
    boolean findByNameAndCompanyId(String name, CompanyID companyId);
}
