package com.salesianostriana.gamesforall.message.repository;


import com.salesianostriana.gamesforall.message.model.Message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long>{

    @Query("SELECT m FROM Message m WHERE m.receptor.id = :userId")
    List<Message> findMessagesById(UUID userId);

}
