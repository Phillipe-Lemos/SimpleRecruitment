package com.recruitment.simplerecruitment.domain.applicationstate;

import com.recruitment.simplerecruitment.domain.Application;
import com.recruitment.simplerecruitment.exception.IllegalStateChangeException;

/**
 * This class treats the apply state.
 *  
 * @author phillipe
 *
 */
public class ApplyAso implements ApplicationStateOperations {

	@Override
	public ApplicationStatus invite(Application application) {
		return ApplicationStatus.INVITED;
	}

	@Override
	public ApplicationStatus reject(Application application) {
		return ApplicationStatus.REJECTED;
	}
	
	@Override
	public ApplicationStatus hire(Application application)  {
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
