package com.ifes.lc.negotiation.history;

import com.ifes.lc.negotiation.csv.CsvBean;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvNumber;

public class NegotiationHistory extends CsvBean{
	
	@CsvBindByName(column="customer_grade")
	private Integer customerGrade;

	@CsvNumber("#.00")
	@CsvBindByName(column="customer_annual_inc")
	private Double customerAnnualInc;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="customer_int_rate_min")
	private Double customerMinIntRate;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="customer_int_rate_max")
	private Double customerMaxIntRate;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="customer_installments_min")
	private Double customerMinInstallments;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="customer_installments_max")
	private Double customerMaxInstallments;

	@CsvNumber("#.00")
	@CsvBindByName(column="customer_amount_max")
	private Double customerMaxAmount;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="customer_amount_min")
	private Double customerMinAmount;

	@CsvNumber("#.00")
	@CsvBindByName(column="bank_int_rate_min")
	private Double bankMinIntRate;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="bank_int_rate_max")
	private Double bankMaxIntRate;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="bank_installments_min")
	private Double bankMinInstallments;

	@CsvNumber("#.00")
	@CsvBindByName(column="bank_installments_max")
	private Double bankMaxInstallments;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="bank_amount_min", required=true)
	private Double bankMinAmount;

	@CsvNumber("#.00")
	@CsvBindByName(column="bank_amount_max")
	private Double bankMaxAmount;
	
	@CsvBindByName(column="negotiation_deal")
	private Integer negotiationDeal;

	@CsvBindByName(column="negotiation_rounds")
	private Integer negotiationRounds;

	@CsvNumber("#.00")
	@CsvBindByName(column="first_bank_installments_proposal")
	private double firstBankInstallmentsProposal;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="first_bank_amount_proposal")
	private double firstBankAmountProposal;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="first_bank_int_rate_proposal")
	private double firstBankIntRateProposal;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="first_customer_installments_proposal")
	private Double firstCustomerInstallmentsProposal;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="first_customer_amount_proposal")
	private Double firstCustomerAmountProposal;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="first_customer_int_rate_proposal")
	private Double firstCustomerIntRateProposal;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="last_installments_proposal")
	private Double lastInstallmentsProposal;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="last_amount_proposal")
	private Double lastAmountProposal;
	
	@CsvNumber("#.00")
	@CsvBindByName(column="last_int_rate_proposal")
	private Double lastIntRateProposal;
	
	@CsvBindByName(column="negotiation_default")
	private Integer negotiationDefault;

	@CsvNumber("#.00")
	@CsvBindByName(column="negotiation_default_prob")
	private Double negotiationDefaultProb;
	
	public Integer getCustomerGrade() {
		return customerGrade;
	}

	public void setCustomerGrade(Integer customerGrade) {
		this.customerGrade = customerGrade;
	}

	public Double getCustomerAnnualInc() {
		return customerAnnualInc;
	}

	public void setCustomerAnnualInc(Double customerAnnualInc) {
		this.customerAnnualInc = customerAnnualInc;
	}

	public Double getCustomerMinIntRate() {
		return customerMinIntRate;
	}

	public void setCustomerMinIntRate(Double customerMinIntRate) {
		this.customerMinIntRate = customerMinIntRate;
	}

	public Double getCustomerMaxIntRate() {
		return customerMaxIntRate;
	}

	public void setCustomerMaxIntRate(Double customerMaxIntRate) {
		this.customerMaxIntRate = customerMaxIntRate;
	}

	public Double getCustomerMinInstallments() {
		return customerMinInstallments;
	}

	public void setCustomerMinInstallments(Double customerMinInstallments) {
		this.customerMinInstallments = customerMinInstallments;
	}

	public Double getCustomerMaxInstallments() {
		return customerMaxInstallments;
	}

	public void setCustomerMaxInstallments(Double customerMaxInstallments) {
		this.customerMaxInstallments = customerMaxInstallments;
	}

	public Double getCustomerMaxAmount() {
		return customerMaxAmount;
	}

	public void setCustomerMaxAmount(Double customerMaxAmount) {
		this.customerMaxAmount = customerMaxAmount;
	}

	public Double getCustomerMinAmount() {
		return customerMinAmount;
	}

	public void setCustomerMinAmount(Double customerMinAmount) {
		this.customerMinAmount = customerMinAmount;
	}

	public Double getBankMinIntRate() {
		return bankMinIntRate;
	}

	public void setBankMinIntRate(Double bankMinIntRate) {
		this.bankMinIntRate = bankMinIntRate;
	}

	public Double getBankMaxIntRate() {
		return bankMaxIntRate;
	}

	public void setBankMaxIntRate(Double bankMaxIntRate) {
		this.bankMaxIntRate = bankMaxIntRate;
	}

	public Double getBankMinInstallments() {
		return bankMinInstallments;
	}

	public void setBankMinInstallments(Double bankMinInstallments) {
		this.bankMinInstallments = bankMinInstallments;
	}

	public Double getBankMaxInstallments() {
		return bankMaxInstallments;
	}

	public void setBankMaxInstallments(Double bankMaxInstallments) {
		this.bankMaxInstallments = bankMaxInstallments;
	}

	public Double getBankMaxAmount() {
		return bankMaxAmount;
	}

	public void setBankMaxAmount(Double bankMaxAmount) {
		this.bankMaxAmount = bankMaxAmount;
	}

	public Double getBankMinAmount() {
		return bankMinAmount;
	}

	public void setBankMinAmount(Double bankMinAmount) {
		this.bankMinAmount = bankMinAmount;
	}

	public Integer getNegotiationDeal() {
		return negotiationDeal;
	}

	public void setNegotiationDeal(Integer negotiationDeal) {
		this.negotiationDeal = negotiationDeal;
	}

	public Integer getNegotiationRounds() {
		return negotiationRounds;
	}

	public void setNegotiationRounds(Integer negotiationRounds) {
		this.negotiationRounds = negotiationRounds;
	}

	public Double getLastInstallmentsProposal() {
		return lastInstallmentsProposal;
	}

	public void setLastInstallmentsProposal(Double lastInstallmentsProposal) {
		this.lastInstallmentsProposal = lastInstallmentsProposal;
	}

	public Double getLastAmountProposal() {
		return lastAmountProposal;
	}

	public void setLastAmountProposal(Double lastAmountProposal) {
		this.lastAmountProposal = lastAmountProposal;
	}

	public Double getLastIntRateProposal() {
		return lastIntRateProposal;
	}

	public void setLastIntRateProposal(Double lastIntRateProposal) {
		this.lastIntRateProposal = lastIntRateProposal;
	}

	public Double getFirstBankInstallmentsProposal() {
		return firstBankInstallmentsProposal;
	}

	public void setFirstBankInstallmentsProposal(Double firstBankInstallmentsProposal) {
		this.firstBankInstallmentsProposal = firstBankInstallmentsProposal;
	}

	public Double getFirstBankAmountProposal() {
		return firstBankAmountProposal;
	}

	public void setFirstBankAmountProposal(Double firstBankAmountProposal) {
		this.firstBankAmountProposal = firstBankAmountProposal;
	}

	public Double getFirstBankIntRateProposal() {
		return firstBankIntRateProposal;
	}

	public void setFirstBankIntRateProposal(Double firstBankIntRateProposal) {
		this.firstBankIntRateProposal = firstBankIntRateProposal;
	}

	public Double getFirstCustomerInstallmentsProposal() {
		return firstCustomerInstallmentsProposal;
	}

	public void setFirstCustomerInstallmentsProposal(Double firstCustomerInstallmentsProposal) {
		this.firstCustomerInstallmentsProposal = firstCustomerInstallmentsProposal;
	}

	public Double getFirstCustomerAmountProposal() {
		return firstCustomerAmountProposal;
	}

	public void setFirstCustomerAmountProposal(Double firstCustomerAmountProposal) {
		this.firstCustomerAmountProposal = firstCustomerAmountProposal;
	}

	public Double getFirstCustomerIntRateProposal() {
		return firstCustomerIntRateProposal;
	}

	public void setFirstCustomerIntRateProposal(Double firstCustomerIntRateProposal) {
		this.firstCustomerIntRateProposal = firstCustomerIntRateProposal;
	}

	public Integer getNegotiationDefault() {
		return negotiationDefault;
	}

	public void setNegotiationDefault(Integer negotiationDefault) {
		this.negotiationDefault = negotiationDefault;
	}

	public Double getNegotiationDefaultProb() {
		return negotiationDefaultProb;
	}

	public void setNegotiationDefaultProb(Double negotiationDefaultProb) {
		this.negotiationDefaultProb = negotiationDefaultProb;
	}

	@Override
	public String toString() {
		return "NegotiationHistory [customerGrade=" + customerGrade + ", customerAnnualInc=" + customerAnnualInc
				+ ", customerMinIntRate=" + customerMinIntRate + ", customerMaxIntRate=" + customerMaxIntRate
				+ ", customerMinInstallments=" + customerMinInstallments + ", customerMaxInstallments="
				+ customerMaxInstallments + ", customerMaxAmount=" + customerMaxAmount + ", customerMinAmount="
				+ customerMinAmount + ", bankMinIntRate=" + bankMinIntRate + ", bankMaxIntRate=" + bankMaxIntRate
				+ ", bankMinInstallments=" + bankMinInstallments + ", bankMaxInstallments=" + bankMaxInstallments
				+ ", bankMinAmount=" + bankMinAmount + ", bankMaxAmount=" + bankMaxAmount + ", negotiationDeal="
				+ negotiationDeal + ", negotiationRounds=" + negotiationRounds + ", firstBankInstallmentsProposal="
				+ firstBankInstallmentsProposal + ", firstBankAmountProposal=" + firstBankAmountProposal
				+ ", firstBankIntRateProposal=" + firstBankIntRateProposal + ", firstCustomerInstallmentsProposal="
				+ firstCustomerInstallmentsProposal + ", firstCustomerAmountProposal=" + firstCustomerAmountProposal
				+ ", firstCustomerIntRateProposal=" + firstCustomerIntRateProposal + ", lastInstallmentsProposal="
				+ lastInstallmentsProposal + ", lastAmountProposal=" + lastAmountProposal + ", lastIntRateProposal="
				+ lastIntRateProposal + ", negotiationDefault=" + negotiationDefault + ", negotiationDefaultProb="
				+ negotiationDefaultProb + "]";
	}
}