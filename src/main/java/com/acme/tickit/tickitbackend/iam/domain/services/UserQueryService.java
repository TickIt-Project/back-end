package com.acme.tickit.tickitbackend.iam.domain.services;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetAllUsersQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetUserByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetUsersByRoleQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetUsersWithRolesInQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    Optional<User> handle(GetUserByIdQuery query);
    List<User> handle(GetAllUsersQuery query);
    List<User> handle(GetUsersByRoleQuery query);
    List<User> handle(GetUsersWithRolesInQuery query);
}
