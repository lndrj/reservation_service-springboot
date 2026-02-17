package org.main.reservation_service.repository;

import jakarta.persistence.LockModeType;
import org.main.reservation_service.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ResourceRepository extends JpaRepository<Resource, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Resource r where r.id = :id")
    Optional<Resource> findByIdForUpdate(UUID id);
}
