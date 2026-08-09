package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PerfumeRepository extends JpaRepository<Perfume, Long>,
                                JpaSpecificationExecutor<Perfume> {



}
