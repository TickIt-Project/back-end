package com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
