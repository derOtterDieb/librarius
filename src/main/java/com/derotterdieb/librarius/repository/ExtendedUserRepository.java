package com.derotterdieb.librarius.repository;

import com.derotterdieb.librarius.domain.ExtendedUser;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ExtendedUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtendedUserRepository extends MongoRepository<ExtendedUser, String> {
}
