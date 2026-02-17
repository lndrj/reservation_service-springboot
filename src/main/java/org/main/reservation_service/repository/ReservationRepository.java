package org.main.reservation_service.repository;

import org.main.reservation_service.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation,UUID> {

    @Query("SELECT COUNT(r) FROM Reservation r " +
            "WHERE r.resource.id = :resourceId " +
            "AND r.status = 'ACTIVE' " +
            "AND r.startDate < :endDate " +
            "AND r.endDate > :startDate")
    long countOverlappingReservations(UUID resourceId, LocalDateTime start, LocalDateTime end);
    List<Reservation> findByResourceIdAndStartDateBetween(UUID resourceId, LocalDateTime from, LocalDateTime to);


}
