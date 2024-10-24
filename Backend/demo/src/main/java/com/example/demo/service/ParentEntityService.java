package com.example.demo.service;

import com.example.demo.entity.ParentEntity;
import com.example.demo.repository.ParentEntityRepository;
import com.example.demo.repository.ChildEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParentEntityService {
    private final ParentEntityRepository parentEntityRepository;
    private final ChildEntityRepository childEntityRepository;

    public ParentEntityService(ParentEntityRepository parentEntityRepository, ChildEntityRepository childEntityRepository) {
        this.parentEntityRepository = parentEntityRepository;
        this.childEntityRepository = childEntityRepository;
    }

    public List<ParentEntity> getAllParents() {
        return parentEntityRepository.findAll();
    }

    public Optional<ParentEntity> getParentById(UUID id) {
        return parentEntityRepository.findById(id);
    }

    public ParentEntity createParent(ParentEntity parentEntity) {
        return parentEntityRepository.save(parentEntity);
    }

    public ParentEntity updateParent(UUID id, ParentEntity updatedParent) {
        return parentEntityRepository.findById(id)
                .map(parent -> {
                    parent.setName(updatedParent.getName());
                    parent.setCode(updatedParent.getCode());
                    return parentEntityRepository.save(parent);
                })
                .orElseThrow(() -> new RuntimeException("Parent not found with id " + id));
    }

    public void deleteParent(UUID parentId) {
        if (childEntityRepository.existsByParentId(parentId)) {
            throw new RuntimeException("Cannot delete parent with ID " + parentId + " because it has associated children.");
        }
        parentEntityRepository.deleteById(parentId);
    }
}
