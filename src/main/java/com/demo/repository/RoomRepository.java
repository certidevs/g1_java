package com.demo.repository;

import com.demo.model.Room;
import com.demo.model.enums.ScreenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // filtrar por número de butacas
    List<Room> findByCapacityGreaterThanEqual(Integer capacity);

    // filtrar por tipo de pantalla
    List<Room> findByScreentype(ScreenType screentype);

    List<Room> findByActiveTrue();
    Optional<Room> findByIdAndActiveTrue(Long id);
}