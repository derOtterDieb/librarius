package com.derotterdieb.librarius.repository;

import com.derotterdieb.librarius.domain.Gear;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Spring Data MongoDB repository for the Gear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GearRepository extends MongoRepository<Gear, String> {
    List<Gear> findAllByGearName(String name);
}
