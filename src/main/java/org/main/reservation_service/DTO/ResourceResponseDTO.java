package org.main.reservation_service.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResourceResponseDTO {
    private UUID id;
    private String name;
    private Integer capacity;
    private String status;
}
