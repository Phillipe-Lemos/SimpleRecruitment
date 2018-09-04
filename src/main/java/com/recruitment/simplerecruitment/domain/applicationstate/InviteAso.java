package com.recruitment.simplerecruitment.domain.applicationstate;

import com.recruitment.simplerecruitment.domain.Application;
import com.recruitment.simplerecruitment.exception.IllegalStateChangeException;

/**
 * This class treats the invite state.
 *  
 * @author phillipe
 *
 */
public class InviteAso implements ApplicationStateOperations {

	@Override
	public ApplicationStatus invite(Application application) {
		throw new IllegalStateChangeException("Change state not allowed.");
	}
	
	@Override
	public ApplicationStatus reject(Application application) {
		return ApplicationStatus.REJECTED;
	}

	@Override
	public ApplicationStatus hire(Application application) {
		return ApplicationStatus.HIRED;
	}

	@Override
	public ApplicationStatus apply(Application application) {
		throw new IllegalStateChangeException("Change state not allowed.");
	}
	
	/**
	 * This method has a debug propouse.
	 */
	@Override
	public String toString(){
		return this.getClass().getName();
	}

}
