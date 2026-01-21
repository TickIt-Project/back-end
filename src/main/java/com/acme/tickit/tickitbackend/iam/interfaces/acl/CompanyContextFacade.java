package com.acme.tickit.tickitbackend.iam.interfaces.acl;

import java.util.UUID;

public interface CompanyContextFacade {
    Boolean ExistsCompanyById(UUID companyId);
}
