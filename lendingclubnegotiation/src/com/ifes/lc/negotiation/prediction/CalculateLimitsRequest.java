package com.ifes.lc.negotiation.prediction;

public class CalculateLimitsRequest {
	
	private Integer grade;
	
	private Double annual_income;

	public CalculateLimitsRequest() {}

	public CalculateLimitsRequest(Integer grade, Double annual_income) {
		this.grade = grade;
		this.annual_income = annual_income;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Double getAnnual_income() {
		return annual_income;
	}

	public void setAnnual_income(Double annual_income) {
		this.annual_income = annual_income;
	}
}
