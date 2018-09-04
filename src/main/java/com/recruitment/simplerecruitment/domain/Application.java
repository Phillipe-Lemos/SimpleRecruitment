package com.recruitment.simplerecruitment.domain;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.recruitment.simplerecruitment.domain.applicationstate.ApplicationStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(
    name = "application",
    uniqueConstraints = @UniqueConstraint(name = "uc_offer_email", columnNames = {"offer_id", "candidate_email"})
)
public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);
	
	@JsonIgnore
	@Id
	@Column(name="id")
	@GeneratedValue
	private Long Id;

	@ManyToOne
	@JoinColumn(name = "offer_id", referencedColumnName = "id")
    @NotNull( message = "Offer can not be null!")
	private Offer offer;
	
    @Column(name="candidate_email", nullable = false)
    @NotNull(message = "Every candidate must have an email!")
    @NotEmpty(message = "Candidate email not be empty!")
    @NotBlank(message = "Candidate email not be empty!")
	private String email;
	
    @Column(name="candidate_resume", nullable = false)
    @NotNull(message = "Every candidate must have a resume!")
    @NotBlank(message = "Every candidate must have a resume!")
    @NotEmpty(message = "Every candidate must have a resume!")
	private String resume;
	
    @Column(name="status", nullable = false)
    @NotNull(message = "Application status can not be null !")
	private ApplicationStatus applicationStatus;
    
    @JsonIgnore
	@Column(name="created", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime startDate;


    public Application(){
    	super();
    	this.startDate = ZonedDateTime.now();
    	this.applicationStatus = ApplicationStatus.APPLIED;
    }
    
	public Application(Offer offer, 
			           String email, 
			           String resume) {
		this();
		this.offer = offer;
		this.email = email;
		this.resume = resume;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		if(applicationStatus != null && this.applicationStatus != applicationStatus){
			LOG.info("Changin from " + this.applicationStatus + " to : " + applicationStatus);
			this.applicationStatus = applicationStatus;
		}
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		result = prime * result + ((applicationStatus == null) ? 0 : applicationStatus.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((offer == null) ? 0 : offer.hashCode());
		result = prime * result + ((resume == null) ? 0 : resume.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Application)) {
			return false;
		}
		Application other = (Application) obj;
		if (Id == null) {
			if (other.Id != null) {
				return false;
			}
		} else if (!Id.equals(other.Id)) {
			return false;
		}
		if (applicationStatus != other.applicationStatus) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (offer == null) {
			if (other.offer != null) {
				return false;
			}
		} else if (!offer.equals(other.offer)) {
			return false;
		}
		if (resume == null) {
			if (other.resume != null) {
				return false;
			}
		} else if (!resume.equals(other.resume)) {
			return false;
		}
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
			return false;
		}
		return true;
	}

	public void invite(){
		this.setApplicationStatus(applicationStatus.invite(this));
	}

	public void reject(){
		this.setApplicationStatus(applicationStatus.reject(this));
	}
	
	public void hire(){
		this.setApplicationStatus(applicationStatus.hire(this));
	}
}
