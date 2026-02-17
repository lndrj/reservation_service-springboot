package org.main.reservation_service.controller;

import org.main.reservation_service.DTO.CreateResourceRequest;
import org.main.reservation_service.DTO.ResourceResponseDTO;
import org.main.reservation_service.entity.Resource;
import org.main.reservation_service.service.ResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService _repositoryService;

    public ResourceController(ResourceService _repositoryService) {
        this._repositoryService = _repositoryService;
    }



    @PostMapping
    public ResponseEntity<ResourceResponseDTO> create(@RequestBody CreateResourceRequest request){
        Resource r = _repositoryService.createResource(request.name(), request.capacity());
        return ResponseEntity.status(HttpStatus.CREATED).body(_repositoryService.toDTO(r));
    }

    @GetMapping
    public List<Resource> getAll(){
        return _repositoryService.getAll();
    }

}
