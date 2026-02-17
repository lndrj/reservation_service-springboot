package org.main.reservation_service.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateReservationRequest(
        UUID resourceId,
        String userId,
        LocalDateTime start,
        LocalDateTime end

){}
