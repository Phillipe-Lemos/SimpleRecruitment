package com.recruitment.simplerecruitment.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recruitment.simplerecruitment.domain.Offer;
import com.recruitment.simplerecruitment.exception.ConfictException;
import com.recruitment.simplerecruitment.exception.ConstraintsViolationException;
import com.recruitment.simplerecruitment.exception.EntityNotFoundException;
import com.recruitment.simplerecruitment.respository.OfferRepository;
import com.recruitment.simplerecruitment.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService {

	private static final Logger LOG = LoggerFactory.getLogger(OfferServiceImpl.class);
	
	private OfferRepository offerRepository;
	
	@Autowired
	public OfferServiceImpl(final OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Offer create(Offer offer) throws ConstraintsViolationException, ConfictException {
		if(offer != null){
			Offer savedOffer;
	        try{
	        	savedOffer = offerRepository.save(offer);
	        } catch(DuplicateKeyException d){
	        	LOG.warn("Some constraints were throw due offer creation", d);
	        	throw new ConfictException(d.getMessage());
	        } catch (DataIntegrityViolationException e){
	            LOG.warn("Some constraints were throw due offer creation", e);
	            throw new ConstraintsViolationException(e.getMessage());
	        } catch(Exception t){
	            LOG.warn("Some constraints were throw due offer creation", t);
	            throw new ConstraintsViolationException(t.getMessage());
	        }
			return savedOffer;
		} else {
			throw new IllegalArgumentException("Missing offer parameter value.");
		}
	}

	public Offer find(Long offerId) throws EntityNotFoundException {
		Offer offer = offerRepository.findOne(offerId); 
		if(offer == null){
			throw new EntityNotFoundException("Could not find entity with id " + offerId + ".");
		}
		return offer;
	}

	public List<Offer> findAll() {
		return offerRepository.findAll();
	}

}
