package com.ifes.lc.negotiation.main;

import java.util.HashMap;
import java.util.Map;

import com.ifes.lc.negotiation.agent.Agent;
import com.ifes.lc.negotiation.attribute.AttributeProposal;
import com.ifes.lc.negotiation.exception.NegotiationUnavailableException;
import com.ifes.lc.negotiation.history.HistoryManager;
import com.ifes.lc.negotiation.history.NegotiationHistory;
import com.ifes.lc.negotiation.prediction.DefaultPredictionResponse;
import com.ifes.lc.negotiation.prediction.DefaultPredictionService;
import com.ifes.lc.negotiation.util.Constants;
import com.ifes.lc.negotiation.util.Log;

public class NegotiationHost {

	public boolean negotiation(Agent agentProposal,
								Agent agentCounterProposal){
        int i = 1;
        boolean initialProposal = true;
        boolean negotiationAccepted = false;

        Log.println("\nInicio da negociacao");


        while (Application.t <= Application.rounds)
        {
            if (i == 1)
                Log.println("\n----------------------- " + Application.t + " -----------------------");

            Log.println(agentProposal.getName() +  ":");
            
            try {
				agentProposal.makeProposal(agentCounterProposal.getInfo());
			} catch (NegotiationUnavailableException e) {
				Log.println("[" + agentProposal.getName() + "] nao possui nenhuma proposta disponivel para [" + agentCounterProposal.getName() + "]");
				
				negotiationAccepted = agentProposal.evaluateOnlyCounterProposal(agentCounterProposal.getActualProposal(), 
																				agentCounterProposal.getInfo());
				
				break;
			}

            if (initialProposal){
                initialProposal = false;
            }
            else
            if (agentProposal.evaluateProposal(agentCounterProposal)){
                negotiationAccepted = true;
                break;
            }

            Agent temp = agentCounterProposal;
            agentCounterProposal = agentProposal;
            agentProposal = temp;


            if (i == 2) {
                i = 1;
                Application.t++;;
            }else
                i++;
        }

        Log.println(1, "\n--------------------------------------------------------");
        Log.print(1, "\tRound [" + Application.t + "] ");
        if (negotiationAccepted){
            Log.print(1, "OK     -> ");
        }else{
            Log.print(1, "NOT OK -> ");
        }
        
        Log.print(1, agentCounterProposal.getName() + " -> ");
        agentCounterProposal.printProposal();
        Log.println(1, "");

        // Saving final negotiation info into history
        saveLastHistoryInfo(negotiationAccepted, agentCounterProposal);
        
        
//      ---------------HISTORICO--------------
        
        Log.println("\nHistorico: ");

        agentProposal.printHistory();
        
        Log.println("\n");

        agentCounterProposal.printHistory();
        
//      --------------------------------------        
        
        Log.println();

        Log.println("--------------------------------------------------------");
        
        return negotiationAccepted;
    }
	
	private void saveLastHistoryInfo(boolean negotiationAccepted, Agent agentProposal) {
		Map<String, AttributeProposal> actualProposal = agentProposal.getActualProposal();
		NegotiationHistory currentHistory = HistoryManager.getInstance().getCurrentHistory();

		Double lastAmount = actualProposal.get(Constants.QUANTIA).getValue();
		Double lastInstallments = actualProposal.get(Constants.PARCELA).getValue();
		Double lastIntRate = actualProposal.get(Constants.JUROS).getValue();

		currentHistory.setNegotiationDeal(negotiationAccepted ? 1 : 0);
        currentHistory.setNegotiationRounds(Application.t);
		currentHistory.setLastAmountProposal(lastAmount);
		currentHistory.setLastInstallmentsProposal(lastInstallments);
		currentHistory.setLastIntRateProposal(lastIntRate);
		
//		Check the last proposal default probability
		Map<String, Double> request = new HashMap<>();
		request.put(actualProposal.get(Constants.JUROS).getNameOnModel(), lastIntRate);
		request.put(actualProposal.get(Constants.QUANTIA).getNameOnModel(), lastAmount);
		request.put(Constants.ANNUAL_INCOME, currentHistory.getCustomerAnnualInc());
		request.put(Constants.GRADE, currentHistory.getCustomerGrade().doubleValue());
		
		DefaultPredictionResponse response = DefaultPredictionService.getInstance().predict(request);
		Log.println("\n\t\tResultado: ");
		Log.println("\t\t\tPredicao: " + response.getPredicted_class());
		Log.println("\t\t\tProb Inadimplencia: " + response.getDelinquent_prob());
		
		currentHistory.setNegotiationDefault(response.isDefault() ? 1 : 0);
		currentHistory.setNegotiationDefaultProb(response.getDelinquent_prob());
	}
	
}
