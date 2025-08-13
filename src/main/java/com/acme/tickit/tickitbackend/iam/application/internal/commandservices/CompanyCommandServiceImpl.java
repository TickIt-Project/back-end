package com.acme.tickit.tickitbackend.iam.application.internal.commandservices;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.CompanyNameAlreadyExistsException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.CompanyNotCreatedException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.JiraEmailAlreadyExistsException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.JiraEmailNullException;
import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.Company;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateCompanyCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.CompanyCode;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.JiraData;
import com.acme.tickit.tickitbackend.iam.domain.services.CompanyCommandService;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyCommandServiceImpl implements CompanyCommandService {
    private final CompanyRepository companyRepository;

    public CompanyCommandServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public UUID handle(CreateCompanyCommand command) {
        if (companyRepository.existsByCompanyName(command.companyName()))
            throw new CompanyNameAlreadyExistsException(command.companyName());
        if (command.isJiraActive() && command.jiraEmail() == null)
            throw new JiraEmailNullException();
        if (command.isJiraActive() && companyRepository.existsByJiraData(new JiraData(command.jiraEmail(), command.jiraPassword())))
            throw new JiraEmailAlreadyExistsException(command.jiraEmail());
        var company = new Company(command);
        try {
            while (companyRepository.existsByCode(company.getCode())) {
                String newWord = UUID.randomUUID().toString().replaceAll("[^A-Z]", "").substring(0, 4);
                company.setCode(new CompanyCode(company.generateCompanyCode(newWord)));
            }
            companyRepository.save(company);
        } catch (Exception e) {
            throw new CompanyNotCreatedException(e.getMessage());
        }
        return company.getId();
    }
}
