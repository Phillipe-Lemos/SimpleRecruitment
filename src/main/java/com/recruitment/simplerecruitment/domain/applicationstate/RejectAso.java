package com.recruitment.simplerecruitment.domain.applicationstate;

import com.recruitment.simplerecruitment.domain.Application;
import com.recruitment.simplerecruitment.exception.IllegalStateChangeException;

/**
 * This class treats the rejected state.
 *  
 * @author phillipe
 *
 */
public class RejectAso implements ApplicationStateOperations {

	@Override
	public ApplicationStatus invite(Application application) {
		throw new IllegalStateChangeException("Change state not allowed.");
	}

	@Override
	public ApplicationStatus reject(Application application) {
		throw new IllegalStateChangeException("Change state not allowed.");
	}

	@Override
	public ApplicationStatus hire(Application application) {
		throw new IllegalStateChangeException("Change state not allowed.");
	}

	@Override
	public ApplicationStatus apply(Application application) {
		throw new IllegalStateChangeException("Change state not allowed.");
	}
	
	@Override
	public String toString(){
		return this.getClass().getName();
	}

}
