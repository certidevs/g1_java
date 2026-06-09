package com.demo.repository;

import com.demo.model.Session;
import com.demo.model.enums.Language;
import com.demo.model.enums.ScreenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByMovie_Id(Long id);

    List<Session> findByLanguage(Language language);

    List<Session> findByStartTime(LocalDateTime startTime);

    List<Session> findByRoom_Screentype(ScreenType screentype);

    List<Session> findByMovie_IdAndStartTimeBetween(Long movieId, LocalDateTime start, LocalDateTime end);

    List<Session> findByRoom_Id(Long id);

    List<Session> findByStartTimeBetweenAndActiveTrue(LocalDateTime start, LocalDateTime end);

    @Query("""
      SELECT s FROM Session s
      WHERE s.active = true
      AND (:language IS NULL OR s.language = :language)
      AND (:screentype IS NULL OR s.room.screentype = :screentype)
      AND (:title IS NULL OR :title = '' OR LOWER(s.movie.title) LIKE LOWER(CONCAT('%', :title, '%')))
      """)
    List<Session> findActiveFiltering(
            @Param("language") Language language,
            @Param("title") String title,
            @Param("screentype") ScreenType screentype
    );

}