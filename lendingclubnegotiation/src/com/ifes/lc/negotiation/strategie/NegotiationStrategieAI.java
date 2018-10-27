package com.ifes.lc.negotiation.strategie;

import java.util.HashMap;
import java.util.Map;

import com.ifes.lc.negotiation.agent.Agent;
import com.ifes.lc.negotiation.attribute.AttributeProposal;
import com.ifes.lc.negotiation.exception.NegotiationUnavailableException;
import com.ifes.lc.negotiation.info.Information;
import com.ifes.lc.negotiation.main.Application;
import com.ifes.lc.negotiation.prediction.DefaultPredictionService;
import com.ifes.lc.negotiation.prediction.NextProposalResponse;
import com.ifes.lc.negotiation.util.Constants;
import com.oracle.tools.packager.Log;

public class NegotiationStrategieAI extends NegotiationStrategie{

	@Override
    public Double computeProposalScore(Agent agent, Map<String, AttributeProposal> proposal){
        Double proposalValue = super.computeProposalScore(agent, proposal);
        return proposalValue;
    }	
	
	
	
	@Override
	public Map<String, AttributeProposal> initialProposal(Agent agent, Map<String, Information> opponentInfo) throws NegotiationUnavailableException {
		Map<String, AttributeProposal> proposal = super.initialProposal(agent, opponentInfo);		
		
		NextProposalResponse response =  nextProposal(proposal, opponentInfo);       
        
		if(response.getLoan_amnt() < 0 || response.getInt_rate() < 0)         	
    		throw new NegotiationUnavailableException();
        
        proposal.get(Constants.JUROS).setValue(response.getInt_rate());        
        proposal.get(Constants.QUANTIA).setValue(response.getLoan_amnt());
        proposal.get(Constants.PARCELA).setValue(response.getInstallments());
        
        return proposal;
	}



	@Override
    public Map<String, AttributeProposal> proposal(Agent agent, Map<String, AttributeProposal> lastProposal, 
    												Map<String, Information> opponentInfo){
        Map<String, AttributeProposal> proposal = super.proposal(agent, lastProposal, opponentInfo);
        
        NextProposalResponse response =  nextProposal(proposal, opponentInfo);
        
        //Get the last acceptable proposal
        if(response.getLoan_amnt() < 0 || response.getInt_rate() < 0) {
        	Log.info("No more aceptable proposals to the Customer");
        	return agent.getActualProposal();
        }
        
        proposal.get(Constants.JUROS).setValue(response.getInt_rate());        
        proposal.get(Constants.QUANTIA).setValue(response.getLoan_amnt());
        proposal.get(Constants.PARCELA).setValue(response.getInstallments());
        
        return proposal;
    }
	
	public NextProposalResponse nextProposal(Map<String, AttributeProposal> proposal, Map<String, Information> opponentInfo) {
        Map<String, Object> proposalRequest = new HashMap<>();
        
        proposal.forEach((key, value) -> {
        	proposalRequest.put(value.getNameOnModel(), value.getValue());
        });
        
        opponentInfo.forEach((key, value) -> {
        	proposalRequest.put(key, value.getDoubleValue());
        });
        
        proposalRequest.put(Constants.ROUND, Application.t);
        
        return  DefaultPredictionService.getInstance().nextProposal(proposalRequest);
	}

}
