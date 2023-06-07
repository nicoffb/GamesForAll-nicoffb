package com.trianaSalesianos.tofuApp.repository;

import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.DietDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DietDaysRepository extends JpaRepository<DietDays, UUID>, JpaSpecificationExecutor<DietDays> {
}
