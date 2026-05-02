package com.demo.repository;

import com.demo.model.Session;
import com.demo.model.enums.Language;
import com.demo.model.enums.ScreenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByMovie_Id(Long id);

    List<Session> findByLanguage(Language language);

    List<Session> findByStartTime(LocalDateTime startTime);

    List<Session> findByRoom_Screentype(ScreenType screentype);


}