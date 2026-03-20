package com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueCoincidence;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueCoincidenceRepository extends JpaRepository<IssueCoincidence, UUID> {

    List<IssueCoincidence> findAllByCompanyID(CompanyID companyID);

    @Query("SELECT DISTINCT ic.companyID.companyId FROM IssueCoincidence ic")
    List<UUID> findDistinctCompanyIds();
}
