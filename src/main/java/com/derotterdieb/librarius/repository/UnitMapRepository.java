package com.derotterdieb.librarius.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.derotterdieb.librarius.domain.UnitMap;

public interface UnitMapRepository extends MongoRepository<UnitMap, String> {
	@Query("{}")
    Page<UnitMap> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<UnitMap> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<UnitMap> findOneWithEagerRelationships(String id);
}
