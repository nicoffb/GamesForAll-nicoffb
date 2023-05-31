package com.salesianostriana.gamesforall.platform;

import com.salesianostriana.gamesforall.platform.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformRepository extends JpaRepository<Platform,Long> {

}
