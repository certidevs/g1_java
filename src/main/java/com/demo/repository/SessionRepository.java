package com.demo.repository;

import com.demo.model.Session;
import com.demo.model.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByMovie_Id(Long id);

    List<Session> findByLanguage(Language language);


}