package com.recruitment.simplerecruitment.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.recruitment.simplerecruitment.domain.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {
	
	@Query("select o, count(a) from Application a inner join a.offer o where o.id = :offerId group by o")
	Offer findOne(@Param("offerId")Long offerId);

}
