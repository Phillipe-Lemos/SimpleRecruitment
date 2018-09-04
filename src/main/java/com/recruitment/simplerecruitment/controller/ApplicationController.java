package com.recruitment.simplerecruitment.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.simplerecruitment.domain.Application;
import com.recruitment.simplerecruitment.domain.Offer;
import com.recruitment.simplerecruitment.exception.BadRequestException;
import com.recruitment.simplerecruitment.exception.ConstraintsViolationException;
import com.recruitment.simplerecruitment.exception.EntityNotFoundException;
import com.recruitment.simplerecruitment.service.ApplicationService;
import com.recruitment.simplerecruitment.service.OfferService;

@RestController
@RequestMapping("rest/applications")
public class ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);
			
	private ApplicationService applicationService;
	
	private OfferService offerService;
	
	@Autowired
	public ApplicationController(final ApplicationService applicationService, final OfferService offerService) {
		this.applicationService = applicationService;
		this.offerService = offerService;
	}
	
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
	public Application createApplication(@Valid @RequestBody Application application) throws ConstraintsViolationException , BadRequestException{
    	try{
    		return applicationService.create(application);
    	}catch(IllegalArgumentException illegalArgumentException){
    		throw new BadRequestException(illegalArgumentException.getMessage());
    	}
    }
    
    @GetMapping("/{offerId}/{email:.+}")
    @ResponseStatus(HttpStatus.OK)
    public Application getApplication(@Valid @PathVariable long offerId, @Valid @PathVariable String email) throws EntityNotFoundException, BadRequestException{
    	if(offerId <= 0){
    		throw new BadRequestException("Incorrect offerId parameter value.");
    	}
    	
    	if(email.isEmpty()){
    		throw new BadRequestException("Incorrect email parameter value.");
    	}
    	
    	LOG.info(">>> Valores: " + offerId  + " email : " + email);
    	
    	try {
	    	Offer offer = offerService.find(offerId);
	    	return applicationService.findApplicationByOfferEmail(offer, email);
    	}catch(IllegalArgumentException illegalArgumentException){
    		throw new BadRequestException(illegalArgumentException.getMessage());
    	}
    }
    
    @GetMapping("/{offerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Application> getApplications(@Valid @PathVariable long offerId) throws EntityNotFoundException, BadRequestException{
    	try{
	    	Offer offer = offerService.find(offerId);
	    	return applicationService.findAllApplicationByOffer(offer);
    	} catch(IllegalArgumentException illegalArgumentException){
    		throw new BadRequestException(illegalArgumentException.getMessage());
    	}
    }
    
    /**
     * End point one cadidate.
     *  
     * @param offerId - Offer identifier
     * @param email   - Candidate email.
     * @throws EntityNotFoundException Throw if neigther parameters identified one Applications
     * @throws ConstraintsViolationException Throw 
     */
    @PutMapping("/reject/{offerId}/{email:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void rejectApplication(@Valid @PathVariable long offerId, @Valid @PathVariable String email)  throws EntityNotFoundException,
                                                                                                                ConstraintsViolationException{
    	LOG.info(">>> RejectApplication " + offerId  + " email : " + email);
    	Offer offer = offerService.find(offerId);
    	applicationService.reject(offer, email);
    }
    
    @PutMapping("/invite/{offerId}/{email:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void inviteCandidate(@Valid @PathVariable long offerId, @Valid @PathVariable String email) throws EntityNotFoundException,
                                                                                                             ConstraintsViolationException{
    	LOG.info(">>> InviteCandidate " + offerId  + " email : " + email);
    	Offer offer = offerService.find(offerId);
    	applicationService.invite(offer, email);
    }
    
    @PutMapping("/recruit/{offerId}/{email:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void recruitCandidate(@Valid @PathVariable long offerId, @Valid @PathVariable String email)throws EntityNotFoundException,
                                                                                                             ConstraintsViolationException{
    	LOG.info(">>> Hire " + offerId  + " email : " + email);
    	Offer offer = offerService.find(offerId);
    	applicationService.hire(offer, email);
    }
}
