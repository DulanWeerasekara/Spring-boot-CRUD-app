package com.example.demo.repository;

import com.example.demo.entity.ChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChildEntityRepository extends JpaRepository<ChildEntity, UUID> {
    boolean existsByParentId(UUID parentId);
}
