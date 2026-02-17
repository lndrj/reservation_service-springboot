package org.main.reservation_service.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceRequestDTO {

    @NotBlank
    private String name;

    private Integer capacity;

    private String status;

}
