package com.recruitment.simplerecruitment.domain.applicationstate;

import com.recruitment.simplerecruitment.domain.Application;

/**
 * Represents the possibles operations in ApplicationState.
 * 
 * @author phillipe
 *
 */
public interface ApplicationStateOperations {

	
	/**
	 * This method is call when a application is created for garantee that any Application start with ApplicationStatus different from APPLIED.
	 * @param application
	 * @return
	 */
	ApplicationStatus apply(Application application);
	
	/**
	 * This method is call when candidate is invite for an interview. 
	 * 
	 * @param application
	 * 
	 * @return INVITED status
	 */
	ApplicationStatus invite(Application application);
	
	/**
	 * This method is call when the candidate didn't pass on interview or your resume
	 * didn't match with job offer requirements.
	 * 
	 * @param application
	 * 
	 * @return REJECTED
	 */
	ApplicationStatus reject(Application application);
	
	/**
	 * This method is call when the candidate pass in interview and he is hired.
	 * 
	 * @param application
	 * @return
	 */
	ApplicationStatus hire(Application application); 
	
}
