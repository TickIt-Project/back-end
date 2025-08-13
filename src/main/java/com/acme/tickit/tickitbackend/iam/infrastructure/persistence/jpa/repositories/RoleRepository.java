package com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.iam.domain.model.entities.Role;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(Roles name);
    boolean existsByName(Roles name);
}
