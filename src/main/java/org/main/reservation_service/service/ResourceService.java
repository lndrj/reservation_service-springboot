package org.main.reservation_service.service;

import jakarta.transaction.Transactional;
import org.main.reservation_service.DTO.ResourceRequestDTO;
import org.main.reservation_service.DTO.ResourceResponseDTO;
import org.main.reservation_service.entity.Resource;
import org.main.reservation_service.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository _resourceRepository;

    public ResourceService(ResourceRepository resourceRepo) {
        this._resourceRepository = resourceRepo;

    }

    public Resource createResource(String name, int capacity){
        Resource r = new Resource();
        r.setName(name);
        r.setCapacity(capacity);
        r.setStatus(Resource.Status.ACTIVE);
        return _resourceRepository.save(r);
    }

    public List<Resource> getAll(){
        return _resourceRepository.findAll();
    }

    public ResourceResponseDTO toDTO(Resource resource){
        var dto = new ResourceResponseDTO();
        dto.setId(resource.getId());
        dto.setName(resource.getName());
        dto.setCapacity(resource.getCapacity());
        dto.setStatus(resource.getStatus().name());
        return dto;
    }

    public Resource toEntity(ResourceRequestDTO dto) {
        var resource = new Resource();
        resource.setName(dto.getName());
        resource.setCapacity(dto.getCapacity());
        resource.setStatus(Resource.Status.valueOf(dto.getStatus()));
        return resource;
    }

}
