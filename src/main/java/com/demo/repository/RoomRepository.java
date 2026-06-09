package com.demo.repository;

import com.demo.model.Room;
import com.demo.model.enums.ScreenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // filtrar por número de butacas (solo activas)
    List<Room> findByCapacityGreaterThanEqualAndActiveTrue(Integer capacity);

    // filtrar por tipo de pantalla (solo activas)
    List<Room> findByScreentypeAndActiveTrue(ScreenType screentype);

    // List<Room> findByActiveTrue();
    Optional<Room> findByIdAndActiveTrue(Long id);

    Long countByScreentypeAndActiveTrue(ScreenType screentype);

    // Query con filtros:
    @Query("""
           SELECT r from Room r
           WHERE r.active = true
           AND (:screenType IS NULL OR r.screentype = :screenType)
           AND (:capacity IS NULL OR r.capacity <= :capacity)
           AND (:price IS NULL OR r.price <= :price)
           AND (:title IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :title, '%')))
           """)
    List<Room> findActiveFiltering(
            @Param("screenType") ScreenType screenType,
            @Param("capacity") Integer capacity,
            @Param("price") Double price,
            @Param("title") String title
    );
}
