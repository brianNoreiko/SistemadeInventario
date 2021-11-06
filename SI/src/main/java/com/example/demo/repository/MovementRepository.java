package com.example.demo.repository;

import com.example.demo.models.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement,Integer> {
    List<Movement> findAllByDateIsAfterAndProductId(LocalDateTime date, Integer id);
    List<Movement> findAllByDateIsAfter(LocalDateTime date);
}
