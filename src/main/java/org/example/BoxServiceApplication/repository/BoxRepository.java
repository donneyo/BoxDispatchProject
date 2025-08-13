package org.example.BoxServiceApplication.repository;

import org.example.BoxServiceApplication.entity.Box;
import org.example.BoxServiceApplication.entity.BoxState;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BoxRepository extends MongoRepository<Box, String> {
    List<Box> findByStateAndBatteryCapacityGreaterThanEqual(BoxState state, int batteryCapacity);
}
