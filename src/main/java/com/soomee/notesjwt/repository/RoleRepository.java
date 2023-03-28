package com.soomee.notesjwt.repository;

import com.soomee.notesjwt.model.Role;
import com.soomee.notesjwt.model.enums.RoleType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(RoleType name);
}
