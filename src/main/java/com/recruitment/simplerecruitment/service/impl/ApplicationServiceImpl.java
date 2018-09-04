package com.recruitment.simplerecruitment.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recruitment.simplerecruitment.domain.Application;
import com.recruitment.simplerecruitment.domain.Offer;
import com.recruitment.simplerecruitment.exception.ConstraintsViolationException;
import com.recruitment.simplerecruitment.exception.EntityNotFoundException;
import com.recruitment.simplerecruitment.respository.ApplicationRepository;
import com.recruitment.simplerecruitment.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationServiceImpl.class);
	
	private ApplicationRepository applicationRepository;
	
	
	@Autowired
	public ApplicationServiceImpl(final ApplicationRepository applicationRepository){
		this.applicationRepository = applicationRepository; 
	}
	
	/**
	 * Create a new instance of Application
	 * 
	 * @param application Application object send in post resquest body.
	 * 
	 * @return An Application instance saved in persistence store.
	 * 
	 * @throws ConstraintsViolationException 
	 *         IllegalArgumentException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Application create(Application application) throws ConstraintsViolationException {
		if(application != null){
			Application appSaved;
			try{
				appSaved = applicationRepository.save(application);
	        } catch (DataIntegrityViolationException e){
	            LOG.warn("Some constraints were throw due incorrect application creation!", e);
	            throw new ConstraintsViolationException(e.getMessage());
	        } catch(Exception t){
	            LOG.warn("Some constraints were throw due offer creation", t);
	            throw new ConstraintsViolationException(t.getMessage());
	        }
			return appSaved;
		} else {
			throw new IllegalArgumentException("Missing application parameter value.");
		}
	}

	
	/**
	 * Fetch a specific job application by an offer and candidate email.
	 *  
	 * @param offer A job offer. 
	 * 
	 * @param email Candidate email for a specific offer
	 * 
	 * @return A specific application from a user identified by his email and by job offer.
	 * 
	 * @throws EntityNotFoundException if there isn't any application to an offer and email.
	 */
	public Application findApplicationByOfferEmail(Offer offer, String email) throws EntityNotFoundException {
		Application app = applicationRepository.findApplicationByOfferIdAndEmail(offer.getId(), email);
		if(app == null){
			throw new EntityNotFoundException("Missing applicataion.");
		}
		return app;
	}

	public List<Application> findAllApplicationByOffer(Offer offer) {
		return applicationRepository.findAllApplicationByOfferId(offer.getId());
	}

	/**
	 * Invite a candidate to an interview.
	 * 
	 * @param Offer   A specific offer replied by a candidate.
	 * 
	 * @param email  Candidate email. 
	 * 
	 * @throws ConstraintsViolationException if update ApplicationState attribute fails
	 *         EntityNotFoundException if neither application was found with passed parameters. 
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void invite(Offer offer, String email) throws ConstraintsViolationException, EntityNotFoundException {
		Application app = applicationRepository.findApplicationByOfferIdAndEmail(offer.getId(), email);
		if(app == null){
			throw new EntityNotFoundException("Missing applicataion.");
		}
		try{
			LOG.info(app.getApplicationStatus().toString());
			app.setApplicationStatus(app.getApplicationStatus().invite(app));
			applicationRepository.save(app);
		} catch(DataIntegrityViolationException dataIntegrityViolationException){
			throw new ConstraintsViolationException(dataIntegrityViolationException.getMessage());
		} 
	}

	/**
	 * Hire a candidate that was succeeded in one interview.
	 * 
	 * @param Offer   A specific offer replied by a candidate.
	 * 
	 * @param email  Candidate email. 
	 * 
	 * @throws ConstraintsViolationException if update ApplicationState attribute fails
	 *         EntityNotFoundException if neither application was found with passed parameters. 
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void hire(Offer offer, String email) throws ConstraintsViolationException, EntityNotFoundException {
		Application app = applicationRepository.findApplicationByOfferIdAndEmail(offer.getId(), email);
		if(app == null){
			throw new EntityNotFoundException("Missing applicataion.");
		}
		try{
			LOG.info(app.getApplicationStatus().toString());
			app.setApplicationStatus(app.getApplicationStatus().hire(app));
			applicationRepository.save(app);
		} catch(DataIntegrityViolationException dataIntegrityViolationException){
			throw new ConstraintsViolationException(dataIntegrityViolationException.getMessage());
		} 
	}

	/**
	 * Dismiss the candidate that was not pass in one interview.
	 * 
	 * @param Offer   A specific offer replied by a candidate.
	 * 
	 * @param email  Candidate email. 
	 * 
	 * @throws ConstraintsViolationException if update ApplicationState attribute fails
	 *         EntityNotFoundException if neither application was found with passed parameters. 
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void reject(Offer offer, String email) throws ConstraintsViolationException, EntityNotFoundException {
		Application app = applicationRepository.findApplicationByOfferIdAndEmail(offer.getId(), email);
		if(app == null){
			throw new EntityNotFoundException("Missing applicataion.");
		}
		try{
			LOG.info(app.getApplicationStatus().toString());
			app.setApplicationStatus(app.getApplicationStatus().reject(app));
			applicationRepository.save(app);
		} catch(DataIntegrityViolationException dataIntegrityViolationException){
			throw new ConstraintsViolationException(dataIntegrityViolationException.getMessage());
		} 
	}
}
