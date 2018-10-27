package com.ifes.lc.negotiation.agent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ifes.lc.negotiation.attribute.Attribute;
import com.ifes.lc.negotiation.attribute.AttributeProposal;
import com.ifes.lc.negotiation.attribute.Item;
import com.ifes.lc.negotiation.history.HistoryManager;
import com.ifes.lc.negotiation.history.NegotiationHistory;
import com.ifes.lc.negotiation.info.Information;
import com.ifes.lc.negotiation.prediction.CalculateLimitsRequest;
import com.ifes.lc.negotiation.prediction.CalculateLimitsResponse;
import com.ifes.lc.negotiation.prediction.DefaultPredictionResponse;
import com.ifes.lc.negotiation.prediction.DefaultPredictionService;
import com.ifes.lc.negotiation.strategie.NegotiationStrategie;
import com.ifes.lc.negotiation.util.Constants;
import com.ifes.lc.negotiation.util.Log;

public class BankAgent extends Agent {

	public BankAgent(String name, Item obj, Map<String, Information> opponentInfo, NegotiationStrategie strategie) {
		super(name, obj, strategie);
		
		loadAttributesLimits(opponentInfo);
	}

	public void loadAttributesLimits(Map<String, Information> opponentInfo) {
		NegotiationHistory history = HistoryManager.getInstance().getCurrentHistory();
		
		Integer grade = (Integer) opponentInfo.get(Constants.GRADE).getValue();
		Double annual_inc = (Double) opponentInfo.get(Constants.ANNUAL_INCOME).getValue();
		
		CalculateLimitsResponse response = DefaultPredictionService.getInstance().calculateFixedLimits(new CalculateLimitsRequest(grade, annual_inc));
		for(Attribute attr: super.obj.getAttrs()) {
			if(attr.getMax() == null && attr.getMin() == null) {
				if(attr.getNameOnModel().equals(Constants.LOAN_AMOUNT)) {
					attr.setMin(response.getMin_amount());
					attr.setMax(response.getMax_amount());
				}else if(attr.getNameOnModel().equals(Constants.INTEREST_RATE)) {
					attr.setMin(response.getMin_int_rate());
					attr.setMax(response.getMax_int_rate());
				}
			}
			
//			Saving into bank's history
			if(attr.getNameOnModel().equals(Constants.LOAN_AMOUNT)) {
				history.setBankMinAmount(attr.getMin());
				history.setBankMaxAmount(attr.getMax());
				
			}else if(attr.getNameOnModel().equals(Constants.INTEREST_RATE)) {
				history.setBankMinIntRate(attr.getMin());
				history.setBankMaxIntRate(attr.getMax());
			
			}else if(attr.getNameOnModel().equals(Constants.INSTALLMENTS)) {
				history.setBankMinInstallments(attr.getMin());
				history.setBankMaxInstallments(attr.getMax());
			}

		}
	}

	private DefaultPredictionResponse predictCounterProposal(Map<String, AttributeProposal> proposal,
			Map<String, Information> opponentInfo) {
		Map<String, Double> request = new HashMap<>();

		Log.println(1, "\t\t\tint_rate    = " + proposal.get(Constants.JUROS).getValue());
//		Log.println(1, "\t\t\tloan_amnt   = " + AMOUNT_REQUIRED);

		for (Entry<String, Information> entry : opponentInfo.entrySet()) {
			Log.println(1, "\t\t\t" + entry.getKey() + " = " + entry.getValue().getDoubleValue());
			request.put(entry.getKey(), entry.getValue().getDoubleValue());
		} 

		request.put(proposal.get(Constants.JUROS).getNameOnModel(), proposal.get(Constants.JUROS).getValue());
		request.put(proposal.get(Constants.QUANTIA).getNameOnModel(), proposal.get(Constants.QUANTIA).getValue());
		
//		request.put("loan_amnt", AMOUNT_REQUIRED);
		
		DefaultPredictionResponse result = DefaultPredictionService.getInstance().predict(request);
		
		Log.println("\n\t\tResultado: ");
		Log.println("\t\t\tPredicao: " + result.getPredicted_class());
		Log.println("\t\t\tProb Inadimplencia: " + result.getDelinquent_prob());
		
		return result;
	}
	
	@Override
	public boolean evaluateOnlyCounterProposal(Map<String, AttributeProposal> proposal,
												Map<String, Information> opponentInfo) {
		
		Log.println(1, "\n\t\tEvaluateOnlyCounterProposal: ");
		DefaultPredictionResponse result = predictCounterProposal(proposal, opponentInfo);
		return !result.isDefault();
	}

	@Override
	public Double evaluateProposalInfo(Double proposalValue, Map<String, AttributeProposal> proposal,
			Map<String, Information> opponentInfo) {
		
		Log.println(1, "\n\t\tEvaluateProposalInfo: ");
		DefaultPredictionResponse result = predictCounterProposal(proposal, opponentInfo);
		if(result.isDefault()) {
			
			Double discountFactor = (result.getDelinquent_prob() - 50.00)/5;
			
			Log.println("\t\tScore Inicial da Proposta: [" + proposalValue + "] Penalidade: [" + discountFactor + "]");
			proposalValue -= discountFactor;
		}
		
		return proposalValue;
	}

}
