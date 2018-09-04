package com.recruitment.simplerecruitment.domain.applicationstate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.recruitment.simplerecruitment.domain.Application;

/**
 * This enum works like a state machine with four implementantions of ApplicationStateOperations. 
 * Wicth one is responsable for change the state of ApplicationState from Application class to 
 * a proper new state.
 * 
 * In my state machine a treat the transitions from :
 * 
 *  APPLIED -> INVITED -> HIRED(final state) 
 *     |       /
 *     REJECTED(final state)
 * 
 * @author phillipe
 *
 */
public enum ApplicationStatus implements ApplicationStateOperations {
	APPLIED(new ApplyAso()),
	INVITED(new InviteAso()),
	REJECTED(new RejectAso()),
	HIRED(new HireAso());

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationStatus.class);
	
	private ApplicationStateOperations operations;
	
	private ApplicationStatus(ApplicationStateOperations operations){
		this.operations = operations;
	}

	@Override
	public ApplicationStatus invite(Application application) {
		LOG.info(">>> invite");
		LOG.info(">>> " + operations.toString());
		return operations.invite(application);
	}
	
	@Override
	public ApplicationStatus reject(Application application) {
		LOG.info(">>> reject");
		LOG.info(">>> " + operations.toString());
		return operations.reject(application);
	}

	@Override
	public ApplicationStatus hire(Application application) {
		LOG.info(">>> hire");
		LOG.info(">>> " + operations.toString());
		return operations.hire(application);
	}

	@Override
	public ApplicationStatus apply(Application application) {
		LOG.info(">>> apply");
		LOG.info(">>> " + operations.toString());
		return operations.apply(application);
	}
}
