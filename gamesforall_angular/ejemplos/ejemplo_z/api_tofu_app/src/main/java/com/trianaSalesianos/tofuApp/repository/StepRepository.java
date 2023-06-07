package com.trianaSalesianos.tofuApp.repository;

import com.trianaSalesianos.tofuApp.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface StepRepository extends JpaRepository<Step, UUID>, JpaSpecificationExecutor<Step> {
}
