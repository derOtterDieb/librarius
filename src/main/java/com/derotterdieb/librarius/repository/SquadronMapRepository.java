package com.derotterdieb.librarius.repository;

import com.derotterdieb.librarius.domain.SquadronMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SquadronMapRepository extends MongoRepository<SquadronMap, String> {
    @Query("{}")
    Page<SquadronMap> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<SquadronMap> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<SquadronMap> findOneWithEagerRelationships(String id);
}
