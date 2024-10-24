package com.example.demo.service;

import com.example.demo.entity.ChildEntity;
import com.example.demo.repository.ChildEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChildEntityService {
    private final ChildEntityRepository childEntityRepository;

    public ChildEntityService(ChildEntityRepository childEntityRepository) {
        this.childEntityRepository = childEntityRepository;
    }

    public List<ChildEntity> getAllChildren() {
        return childEntityRepository.findAll();
    }

    public Optional<ChildEntity> getChildById(UUID id) {
        return childEntityRepository.findById(id);
    }

    public ChildEntity createChild(ChildEntity childEntity) {
        return childEntityRepository.save(childEntity);
    }

    public ChildEntity updateChild(UUID id, ChildEntity updatedChild) {
        return childEntityRepository.findById(id)
                .map(child -> {
                    child.setName(updatedChild.getName());
                    child.setCode(updatedChild.getCode());
                    child.setParent(updatedChild.getParent());
                    return childEntityRepository.save(child);
                })
                .orElseThrow(() -> new RuntimeException("Child not found with id " + id));
    }

    public void deleteChild(UUID id) {
        childEntityRepository.deleteById(id);
    }
}
