package com.recruitment.simplerecruitment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.simplerecruitment.domain.Offer;
import com.recruitment.simplerecruitment.exception.BadRequestException;
import com.recruitment.simplerecruitment.exception.ConfictException;
import com.recruitment.simplerecruitment.exception.ConstraintsViolationException;
import com.recruitment.simplerecruitment.exception.EntityNotFoundException;
import com.recruitment.simplerecruitment.service.OfferService;

@RestController
@RequestMapping("rest/offers")
public class OfferController {

	
	private OfferService offerService;
	
	@Autowired
	public OfferController(final OfferService offerService){
		this.offerService = offerService;
	}
	
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
	public Offer createOffer(@Valid @RequestBody Offer offer) throws ConstraintsViolationException, ConfictException, BadRequestException{
    	try{
    		return offerService.create(offer);
    	}catch(IllegalArgumentException illegalArgumentException){
    		throw new BadRequestException(illegalArgumentException.getMessage());
    	}
    }
	
    @GetMapping("/{offerId}")
    public Offer getOffer(@Valid @PathVariable long offerId) throws EntityNotFoundException, BadRequestException{
    	if(offerId <= 0){
    		throw new BadRequestException("Incorrect offerId parameter value.");
    	}
    	
    	try {
    		return offerService.find(offerId);
    	}catch(IllegalArgumentException illegalArgumentException){
    		throw new BadRequestException(illegalArgumentException.getMessage());
    	} 
    }
    
    @GetMapping("/all")
    public List<Offer> findOffers(){
    	return offerService.findAll();
    }
    
}
