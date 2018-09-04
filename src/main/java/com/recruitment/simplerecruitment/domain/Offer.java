package com.recruitment.simplerecruitment.domain;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(
    name = "offer",
    uniqueConstraints = @UniqueConstraint(name = "uc_jobtitle", columnNames = {"job_title"})
)
public class Offer {
	
	@Id
	@Column(name="id")
    @GeneratedValue
	private Long id;
	
    @Column(name="job_title", nullable = false)
    @NotNull(message = "Job title can not be null!")
    @NotEmpty(message = "Job title can not be empty!")
    @NotBlank(message = "Job title can not be empty!")
	private String jobTitle;
    
	@Column(name="created", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime startDate;
	
	@Transient
	private Long numberOfApplications;
	
	public Offer(){
		super();
		this.startDate = ZonedDateTime.now();
		this.numberOfApplications = null;
	}
	
	public Offer(Long id, String jobTitle, ZonedDateTime startDate, Long numberOfApplications) {
		super();
		this.id = id;
		this.jobTitle = jobTitle;
		this.startDate = startDate;
		this.numberOfApplications = numberOfApplications;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Long getNumberOfApplications() {
		return numberOfApplications;
	}

	public void setNumberOfApplications(Long numberOfApplications) {
		this.numberOfApplications = numberOfApplications;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobTitle == null) ? 0 : jobTitle.hashCode());
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
		if (!(obj instanceof Offer)) {
			return false;
		}
		Offer other = (Offer) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (jobTitle == null) {
			if (other.jobTitle != null) {
				return false;
			}
		} else if (!jobTitle.equals(other.jobTitle)) {
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
}
