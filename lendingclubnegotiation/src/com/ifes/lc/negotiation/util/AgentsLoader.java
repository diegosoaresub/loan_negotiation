package com.ifes.lc.negotiation.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.ifes.lc.negotiation.agent.BankAgent;
import com.ifes.lc.negotiation.agent.GenericAgent;
import com.ifes.lc.negotiation.attribute.AttributeType;
import com.ifes.lc.negotiation.attribute.Item;
import com.ifes.lc.negotiation.history.HistoryManager;
import com.ifes.lc.negotiation.history.NegotiationHistory;
import com.ifes.lc.negotiation.info.Information;
import com.ifes.lc.negotiation.strategie.NegotiationStrategie;
import com.ifes.lc.negotiation.strategie.NegotiationStrategieAI;

public class AgentsLoader {

	private int numGrades = 7;
	
	private List<Integer> grades;
	
	public AgentsLoader(int numTests) {
		
		grades = new ArrayList<>();
		for(int i = 1; i <= numGrades; i++)
			grades.addAll(Collections.nCopies(numTests/numGrades, i));
		
		Collections.shuffle(grades);
	}
	
	private int nextGrade() {
		return grades.remove(0);
	}
	
	public GenericAgent loadCustomer() {
		Double minInterest = Util.round2Places(ThreadLocalRandom.current().nextDouble(6.2, 20.0));
		Double maxInterest = Util.round2Places(
				minInterest + minInterest * ((30 + ThreadLocalRandom.current().nextDouble(0, 101)) / 100.0));

		Double minInstallments = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 40));
		Double maxInstallments = Math.ceil(
				minInstallments + minInstallments * ((20 + ThreadLocalRandom.current().nextDouble(1, 101)) / 100));

		Double minAmount = Double.valueOf(ThreadLocalRandom.current().nextDouble(1000, 40000));
		Double maxAmount = Math.ceil(
				minAmount + minAmount * ((20 + ThreadLocalRandom.current().nextDouble(1, 101)) / 100));
		
		
		Item productBuyer = new Item("Emprestimo");
		productBuyer.addAttribute(Constants.JUROS, Constants.INTEREST_RATE, 0.6, 0.2, minInterest, maxInterest, AttributeType.COST);
		productBuyer.addAttribute(Constants.PARCELA, Constants.INSTALLMENTS, 0.1, 0.2, minInstallments, maxInstallments, AttributeType.BENEFIT);
		productBuyer.addAttribute(Constants.QUANTIA, Constants.LOAN_AMOUNT, 0.3, 0.2, minAmount, maxAmount, AttributeType.BENEFIT);
		
		Map<String, Information> info = new HashMap<>();
		info.put(Constants.GRADE, new Information(Constants.GRADE, nextGrade()));
		info.put(Constants.ANNUAL_INCOME, new Information(Constants.ANNUAL_INCOME, ThreadLocalRandom.current().nextDouble(10000, 80000)));

		info.put(Constants.TOTAL_CURRENT_BALANCE, new Information(Constants.TOTAL_CURRENT_BALANCE,
				ThreadLocalRandom.current().nextDouble(10_000, 270_000)));

		info.put(Constants.DEBT_TO_INCOME_RATIO, new Information(Constants.DEBT_TO_INCOME_RATIO,
				ThreadLocalRandom.current().nextDouble(5, 30)));

		info.put(Constants.REVOLVING_BALANCE, new Information(Constants.REVOLVING_BALANCE,
				ThreadLocalRandom.current().nextDouble(2_0000, 25_000)));

		info.put(Constants.REVOLVING_UTILIZATION_RATE, new Information(Constants.REVOLVING_UTILIZATION_RATE,
				ThreadLocalRandom.current().nextDouble(15, 85)));

		info.put(Constants.TOTAL_ACCOUNTS, new Information(Constants.TOTAL_ACCOUNTS,
				ThreadLocalRandom.current().nextInt(2, 30)));

		NegotiationHistory currentHistory = HistoryManager.getInstance().getCurrentHistory();
		
		currentHistory.setCustomerAnnualInc(info.get(Constants.ANNUAL_INCOME).getDoubleValue());
		currentHistory.setCustomerGrade(info.get(Constants.GRADE).getIntegerValue());

		currentHistory.setCustomerTotalCurrentBalance(info.get(Constants.TOTAL_CURRENT_BALANCE).getDoubleValue());
		currentHistory.setCustomerDebtToIncomeRatio(info.get(Constants.DEBT_TO_INCOME_RATIO).getDoubleValue());
		currentHistory.setCustomerRevolvingBalance(info.get(Constants.REVOLVING_BALANCE).getDoubleValue());
		currentHistory.setCustomerRevolvingUtilization(info.get(Constants.REVOLVING_UTILIZATION_RATE).getDoubleValue());
		currentHistory.setCustomerTotalOpenAcc(info.get(Constants.TOTAL_ACCOUNTS).getIntegerValue());

		currentHistory.setCustomerMinIntRate(minInterest);
		currentHistory.setCustomerMaxIntRate(maxInterest);
		
		currentHistory.setCustomerMinInstallments(minInstallments);
		currentHistory.setCustomerMaxInstallments(maxInstallments);
		
		currentHistory.setCustomerMinAmount(minAmount);
		currentHistory.setCustomerMaxAmount(maxAmount);

		return new GenericAgent(Constants.CUSTOMER, productBuyer, new NegotiationStrategie(), info);
	}

	public GenericAgent loadManager() {
		Double minInterest = Util.round2Places(ThreadLocalRandom.current().nextDouble(6.2, 20.0));
		Double maxInterest = Util.round2Places(
				minInterest + minInterest * ((30 + ThreadLocalRandom.current().nextDouble(0, 101)) / 100.0));

		Double minInstallments = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 40));
		Double maxInstallments = Math.ceil(
				minInstallments + minInstallments * ((20 + ThreadLocalRandom.current().nextDouble(1, 101)) / 100));

		Double minAmount = Double.valueOf(ThreadLocalRandom.current().nextDouble(1000, 40000));
		Double maxAmount = Math.ceil(
				minAmount + minAmount * ((20 + ThreadLocalRandom.current().nextDouble(1, 101)) / 100));
		
		
		Item productManager = new Item("Emprestimo");
		productManager.addAttribute(Constants.JUROS, Constants.INTEREST_RATE, 0.6, 0.2, minInterest, maxInterest, AttributeType.BENEFIT);
		productManager.addAttribute(Constants.PARCELA, Constants.INSTALLMENTS, 0.1, 0.2, minInstallments, maxInstallments, AttributeType.COST);
		productManager.addAttribute(Constants.QUANTIA, Constants.LOAN_AMOUNT, 0.3, 0.2, minAmount, maxAmount, AttributeType.COST);
		
		NegotiationHistory currentHistory = HistoryManager.getInstance().getCurrentHistory();
		
		currentHistory.setBankMinIntRate(minInterest);
		currentHistory.setBankMaxIntRate(maxInterest);
		
		currentHistory.setBankMinInstallments(minInstallments);
		currentHistory.setBankMaxInstallments(maxInstallments);
		
		currentHistory.setBankMinAmount(minAmount);
		currentHistory.setBankMaxAmount(maxAmount);

		return new GenericAgent(Constants.BANK, productManager, new NegotiationStrategie());

	}
	
	public BankAgent loadManagerAI(Map<String, Information> info) {
		Double minInstallments = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 60));
		Double maxInstallments = Math.ceil(
				minInstallments + minInstallments * ((20 + ThreadLocalRandom.current().nextDouble(1, 101)) / 100));

		Item productSeller = new Item("Emprestimo");
		
//		These attributes limits will be calculated through machine learning algorithm
		productSeller.addAttribute(Constants.JUROS, Constants.INTEREST_RATE, 0.7, 0.2, AttributeType.BENEFIT);
		productSeller.addAttribute(Constants.QUANTIA, Constants.LOAN_AMOUNT, 0.2, 0.2, AttributeType.COST);

//		Attribute limit calculated manually
		productSeller.addAttribute(Constants.PARCELA, Constants.INSTALLMENTS, 0.1, 0.2, minInstallments, maxInstallments, AttributeType.COST);		

		return new BankAgent(Constants.BANK, productSeller, info, new NegotiationStrategieAI());		
	}

}
