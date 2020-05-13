package com.derotterdieb.librarius.repository;

import com.derotterdieb.librarius.domain.Gear;
import com.derotterdieb.librarius.domain.Unit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Unit entity.
 */
@Repository
public interface UnitRepository extends MongoRepository<Unit, String> {

    @Query("{}")
    Page<Unit> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Unit> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Unit> findOneWithEagerRelationships(String id);

    List<Unit> findAllByUnitName(String name);
}
