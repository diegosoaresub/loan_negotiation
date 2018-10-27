package com.ifes.lc.negotiation.prediction;

public class NextProposalResponse {
	
	private Double int_rate;
	
	private Double loan_amnt;
	
	private Double installments;

	public Double getInt_rate() {
		return int_rate;
	}

	public void setInt_rate(Double int_rate) {
		this.int_rate = int_rate;
	}

	public Double getLoan_amnt() {
		return loan_amnt;
	}

	public void setLoan_amnt(Double loan_amnt) {
		this.loan_amnt = loan_amnt;
	}

	public Double getInstallments() {
		return installments;
	}

	public void setInstallments(Double installments) {
		this.installments = installments;
	}
	
}
