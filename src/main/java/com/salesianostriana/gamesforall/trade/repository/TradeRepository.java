package com.salesianostriana.gamesforall.trade.repository;


import com.salesianostriana.gamesforall.trade.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade,Long>, JpaSpecificationExecutor<Trade> {
}
