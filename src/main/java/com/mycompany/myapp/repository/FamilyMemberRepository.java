package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FamilyMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the FamilyMember entity.
 */
@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    @Query(value = "select distinct familyMember from FamilyMember familyMember left join fetch familyMember.volunteers",
        countQuery = "select count(distinct familyMember) from FamilyMember familyMember")
    Page<FamilyMember> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct familyMember from FamilyMember familyMember left join fetch familyMember.volunteers")
    List<FamilyMember> findAllWithEagerRelationships();

    @Query("select familyMember from FamilyMember familyMember left join fetch familyMember.volunteers where familyMember.id =:id")
    Optional<FamilyMember> findOneWithEagerRelationships(@Param("id") Long id);

}
