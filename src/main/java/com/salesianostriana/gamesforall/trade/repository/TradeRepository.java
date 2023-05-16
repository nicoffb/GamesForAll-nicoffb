package com.salesianostriana.gamesforall.trade.repository;


import com.salesianostriana.gamesforall.trade.model.Trade;
import com.salesianostriana.gamesforall.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade,Long>, JpaSpecificationExecutor<Trade> {

    @Query("SELECT SUM(t.score) FROM Trade t WHERE t.seller = :user")
    Double getSumOfScoresByUser(User user);
}
