package org.main.reservation_service.DTO;

public record CreateResourceRequest (
    String name,
    int capacity
            ){}
