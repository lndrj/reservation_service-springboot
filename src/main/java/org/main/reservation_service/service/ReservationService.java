package org.main.reservation_service.service;

import jakarta.transaction.Transactional;
import org.main.reservation_service.DTO.ReservationRequestDTO;
import org.main.reservation_service.DTO.ReservationResponseDTO;
import org.main.reservation_service.DTO.ResourceRequestDTO;
import org.main.reservation_service.DTO.ResourceResponseDTO;
import org.main.reservation_service.entity.Reservation;
import org.main.reservation_service.entity.Resource;
import org.main.reservation_service.exceptions.CapacityExceededException;
import org.main.reservation_service.exceptions.ResourceNotFoundException;
import org.main.reservation_service.repository.ReservationRepository;
import org.main.reservation_service.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReservationService {

    private final ResourceRepository _resourceRepository;
    private final ReservationRepository _reservationRepository;

    public ReservationService(ResourceRepository resourceRepo, ReservationRepository reservationRepo) {
        this._resourceRepository = resourceRepo;
        this._reservationRepository = reservationRepo;
    }

    public Resource createResource(String name, int capacity){
        Resource r = new Resource();
        r.setName(name);
        r.setCapacity(capacity);
        r.setStatus(Resource.Status.ACTIVE);
        return _resourceRepository.save(r);
    }

    @Transactional
    public Reservation createReservation(UUID resourceId, String userId,
                                         LocalDateTime start, LocalDateTime end) {

        //lock - findbyidforupdate custom query with lock
        Resource r = _resourceRepository.findByIdForUpdate(resourceId).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        if(r.getStatus() == Resource.Status.INACTIVE){
            throw new RuntimeException("Resource is Inactive");
        }

        //In lock
        long count = _reservationRepository.countOverlappingReservations(resourceId, start, end);
        if (count >= r.getCapacity()) {
            throw new CapacityExceededException("Resource capacity exceeded in this time window");
        }

        //Res creation
        Reservation res = new Reservation();
        res.setResource(r);
        res.setUserId(userId);
        res.setStartDate(start);
        res.setEndDate(end);
        res.setStatus(Reservation.Status.ACTIVE);
        res.setCreatedAt(LocalDateTime.now());

        return _reservationRepository.save(res);
    }



        @Transactional
    public void cancelReservation(UUID reservationId){
        Reservation r = _reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        r.setStatus(Reservation.Status.CANCELED);
        _reservationRepository.save(r);
    }

    public ReservationResponseDTO toDTO(Reservation reservation){
        var dto = new ReservationResponseDTO();
        dto.setId(reservation.getId());
        dto.setResourceId(reservation.getResource().getId());
        dto.setUserId(reservation.getUserId());
        dto.setStartDate(reservation.getStartDate());
        dto.setEndDate(reservation.getEndDate());
        dto.setStatus(reservation.getStatus().name());
        dto.setCreatedAt(reservation.getCreatedAt());
        return dto;
    }

    public Reservation toEntity(ReservationRequestDTO dto) {
        var reservation = new Reservation();
        reservation.setId(dto.getResourceId());
        reservation.setUserId(dto.getUserId());
        reservation.setStartDate(dto.getStartDate());
        reservation.setEndDate(dto.getEndDate());
        return reservation;
    }


}
