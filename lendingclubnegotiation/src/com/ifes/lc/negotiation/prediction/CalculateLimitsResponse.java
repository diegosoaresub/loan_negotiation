package com.ifes.lc.negotiation.prediction;

public class CalculateLimitsResponse {

	private Double min_int_rate;
	
	private Double max_int_rate;
	
	private Double min_amount;
	
	private Double max_amount;

	public CalculateLimitsResponse() {}
	
	public CalculateLimitsResponse(Double min_int_rate, Double max_int_rate, Double min_amount, Double max_amount) {
		this.min_int_rate = min_int_rate;
		this.max_int_rate = max_int_rate;
		this.min_amount = min_amount;
		this.max_amount = max_amount;
	}

	public Double getMin_int_rate() {
		return min_int_rate;
	}

	public void setMin_int_rate(Double min_int_rate) {
		this.min_int_rate = min_int_rate;
	}

	public Double getMax_int_rate() {
		return max_int_rate;
	}

	public void setMax_int_rate(Double max_int_rate) {
		this.max_int_rate = max_int_rate;
	}

	public Double getMin_amount() {
		return min_amount;
	}

	public void setMin_amount(Double min_amount) {
		this.min_amount = min_amount;
	}

	public Double getMax_amount() {
		return max_amount;
	}

	public void setMax_amount(Double max_amount) {
		this.max_amount = max_amount;
	}
}
