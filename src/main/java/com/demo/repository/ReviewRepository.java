package com.demo.repository;

import com.demo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovie_Title(String title);

    List<Review> findByMovie_IdOrderByCreationDateDesc(Long movieId);


}