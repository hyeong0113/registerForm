package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Baptism;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Baptism entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaptismRepository extends JpaRepository<Baptism, Long> {

}
