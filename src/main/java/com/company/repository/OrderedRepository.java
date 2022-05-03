package com.company.repository;

import com.company.models.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OrderedRepository extends JpaRepository<Ordered, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ORDERED e where (e.fk_order = :fk_order and e.fk_medication = :fk_medication)", nativeQuery = true)
    void deleteById(@Param("fk_order")Integer fk_order, @Param("fk_medication")Integer fk_medication);
}
