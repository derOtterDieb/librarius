package com.derotterdieb.librarius.repository;

import com.derotterdieb.librarius.domain.ArmyList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the ArmyList entity.
 */
@Repository
public interface ArmyListRepository extends MongoRepository<ArmyList, String> {

    @Query("{}")
    Page<ArmyList> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<ArmyList> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<ArmyList> findOneWithEagerRelationships(String id);
}
