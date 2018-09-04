package com.recruitment.simplerecruitment.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.recruitment.simplerecruitment.domain.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

	@Query("select a from Application a where a.offer.id = :offerId")
	List<Application> findAllApplicationByOfferId(@Param("offerId")Long offerId);
	
	@Query("select a from Application a where a.offer.id = ?1 and a.email = ?2")
	Application findApplicationByOfferIdAndEmail(Long offerId, String email);
	
}
