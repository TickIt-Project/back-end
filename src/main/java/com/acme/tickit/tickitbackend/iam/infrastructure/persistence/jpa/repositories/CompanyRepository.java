package com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.Company;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.CompanyCode;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.JiraData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    boolean existsByCompanyName(String companyName);
    boolean existsByCode(CompanyCode companyCode);
    boolean existsByJiraData(JiraData jiraData);
    Optional<Company> findById(UUID id);
}
