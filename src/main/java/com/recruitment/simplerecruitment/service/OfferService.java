package com.recruitment.simplerecruitment.service;

import java.util.List;

import com.recruitment.simplerecruitment.domain.Offer;
import com.recruitment.simplerecruitment.exception.ConfictException;
import com.recruitment.simplerecruitment.exception.ConstraintsViolationException;
import com.recruitment.simplerecruitment.exception.EntityNotFoundException;

public interface OfferService {
	
	Offer create(Offer offer) throws ConstraintsViolationException, ConfictException;
	
	Offer find(Long offerId) throws EntityNotFoundException;
	
    List<Offer> findAll();

}
