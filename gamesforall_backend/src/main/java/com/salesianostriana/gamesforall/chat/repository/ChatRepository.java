package com.salesianostriana.gamesforall.chat.repository;



import com.salesianostriana.gamesforall.chat.model.Chat;
import com.salesianostriana.gamesforall.chat.model.ChatPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, ChatPK> {

    @Query("SELECT c FROM Chat c JOIN FETCH c.messages WHERE c.user1.id = :userId OR c.user2.id = :userId")
    List<Chat> findChats(UUID userId);

}
