package com.demo.service;

import com.demo.model.Director;
import com.demo.repository.DirectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;

    public Director findOrCreate(String name) {
        return directorRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Director newDirector = new Director();
                    newDirector.setName(name);
                    return directorRepository.save(newDirector);
                });
    }
}
