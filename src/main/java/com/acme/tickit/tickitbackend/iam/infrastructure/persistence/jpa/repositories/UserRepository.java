package com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.CompanyCode;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.PersonalData;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);
    boolean existsByPersonalData_Name(String personalDataName);
    boolean existsByPersonalData_Email(String personalDataEmail);
    Boolean existsByCompany_CodeAndRole_Name(CompanyCode companyCode, Roles roleName);
}
