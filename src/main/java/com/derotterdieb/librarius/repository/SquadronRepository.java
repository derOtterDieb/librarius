package com.derotterdieb.librarius.repository;

import com.derotterdieb.librarius.domain.Squadron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SquadronRepository extends MongoRepository<Squadron, String> {
    @Query("{}")
    Page<Squadron> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Squadron> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Squadron> findOneWithEagerRelationships(String id);
}
