package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RegisterMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RegisterMember entity.
 */
@Repository
public interface RegisterMemberRepository extends JpaRepository<RegisterMember, Long> {

    @Query(value = "select distinct registerMember from RegisterMember registerMember left join fetch registerMember.volunteers",
        countQuery = "select count(distinct registerMember) from RegisterMember registerMember")
    Page<RegisterMember> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct registerMember from RegisterMember registerMember left join fetch registerMember.volunteers")
    List<RegisterMember> findAllWithEagerRelationships();

    @Query("select registerMember from RegisterMember registerMember left join fetch registerMember.volunteers where registerMember.id =:id")
    Optional<RegisterMember> findOneWithEagerRelationships(@Param("id") Long id);

}
