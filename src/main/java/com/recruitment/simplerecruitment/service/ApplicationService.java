package com.recruitment.simplerecruitment.service;

import java.util.List;

import com.recruitment.simplerecruitment.domain.Application;
import com.recruitment.simplerecruitment.domain.Offer;
import com.recruitment.simplerecruitment.domain.applicationstate.ApplicationStatus;
import com.recruitment.simplerecruitment.exception.ConstraintsViolationException;
import com.recruitment.simplerecruitment.exception.EntityNotFoundException;

public interface ApplicationService {
	
	Application create(Application application) throws ConstraintsViolationException;
	
	Application findApplicationByOfferEmail(Offer offer, String email) throws EntityNotFoundException;
	
	List<Application> findAllApplicationByOffer(Offer offer);
	
	void invite(Offer offer, String email) throws ConstraintsViolationException, EntityNotFoundException;
	
	void hire(Offer offer, String email) throws ConstraintsViolationException, EntityNotFoundException;
	
	void reject(Offer offer, String email) throws ConstraintsViolationException, EntityNotFoundException;

}