package es.codeurjc.backend.repository;

import org.springframework.stereotype.Component;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.model.ActionChronoWrapper;

@Component
public interface ActionChronoWrapperRepository extends JpaRepository<ActionChronoWrapper, Long>{
    
    
}
