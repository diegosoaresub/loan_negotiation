package com.ifes.lc.negotiation.main;

import com.ifes.lc.negotiation.agent.Agent;
import com.ifes.lc.negotiation.history.HistoryManager;
import com.ifes.lc.negotiation.history.NegotiationHistory;
import com.ifes.lc.negotiation.info.Information;
import com.ifes.lc.negotiation.util.AgentsLoader;
import com.ifes.lc.negotiation.util.Log;

/**
 * Created by diegosoaresub on 28/05/17.
 */

/*
			  ----- GOOD ----- 
      int_rate      grade   annual_inc  loan_amnt
mean     13,34       2,57    72.626,62  13.360,64
std       4,26       1,25    56.405,65   7.933,94
min       5,42       1,00     4.000,00     500,00
25%      10,16       2,00    45.000,00   7.200,00
50%      13,11       2,00    62.000,00  12.000,00
75%      16,20       3,00    88.000,00  18.000,00
max      26,06       7,00 7.141.778,00  35.000,00

*/

/*
			----- DELINQUENT ----- 
      int_rate      grade   annual_inc  loan_amnt
mean     15,63       3,47    69.600,50  15.450,10
std       4,62       1,34    67.788,17   8.595,87
min       5,32       1,00       690,00     900,00
25%      12,42       3,00    43.000,00   9.000,00
50%      15,31       3,00    60.000,00  14.175,00
75%      18,49       4,00    83.000,00  20.000,00
max      30,99       7,00 9.500.000,00  40.000,00

*/


public class Application {

	static Agent agentProposal;

	static Agent agentCounterProposal;

	static int negotiationsTotal = 700;

	static int testTotal = 1;

	static NegotiationHistory currentHistory;
	
	static HistoryManager historicManager = HistoryManager.getInstance();
	
	static AgentsLoader loader = new AgentsLoader(negotiationsTotal);

	static String EXECUTION_TYPE = "FIXED";
//	static String EXECUTION_TYPE = "IA";
	
	public static int rounds = 10;
	
	public static int t = 1;
	
	public static void main(String[] args) {
		Log.level = 1;

		negociacoesFixas();
	}

	public static void negociacoesFixas() {
		NegotiationHost host = new NegotiationHost();

		for (int test = 0; test < testTotal; test++) {
			for (int i = 0; i < negotiationsTotal; i++) {
				t = 1;

				Log.print(1, (i + 1) + ": ");
				
				currentHistory = historicManager.newHistory();
				
				carregaDadosFixos();

				host.negotiation(agentProposal, agentCounterProposal);
				
				historicManager.saveCurrentHistory();
			}
			
			if(EXECUTION_TYPE.equals("FIXED"))			
				historicManager.saveAllToFile("result_fixed.csv");
			else
				historicManager.saveAllToFile("result_ai.csv");
			
			historicManager.resetAllHistory();
		}
	}

	public static void carregaDadosFixos() {
		agentProposal = loader.loadCustomer();
		
		if(EXECUTION_TYPE.equals("FIXED"))
			agentCounterProposal = loader.loadManager();
		else
			agentCounterProposal = loader.loadManagerAI(agentProposal.getInfo());
		
		Log.println(1, "");
		printLimits(agentProposal);
		Log.println(1, "");
		printLimits(agentCounterProposal);
		Log.println(1, "");
	}

	public static void printLimits(Agent agent) {		
		Log.print(1, "Dados " + agent.getName() + " -> ");

		agent.getObj().getAttrs().forEach(a -> {
			Log.print(1, a.getName() + ": " + a.getMin() + " - " + a.getMax() + " ");
		});	
		
		Log.println(1, "");
		
		for(Information info: agent.getInfo().values()) {
			Log.println(1, "\t" + info.getName() + ": " + info.getValue());
		}
	}	
}
