package com.company.repository;

import com.company.models.StorageLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<StorageLocation, Integer> {
}
