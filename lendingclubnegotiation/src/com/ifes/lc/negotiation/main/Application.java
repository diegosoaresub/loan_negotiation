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
revol_bal
count      mean       std  min      25%       50%       75%           max
0  135.092,00 17.925,58 26.955,66 0,00 6.351,00 12.036,50 21.345,00  2.568.995,00
1  238.176,00 15.918,07 20.526,18 0,00 6.264,00 11.167,00 19.330,00  1.743.266,00
2  228.323,00 15.615,38 20.548,70 0,00 5.921,00 10.922,00 19.247,50  1.746.716,00
3  129.937,00 15.289,63 19.578,69 0,00 5.671,00 10.768,00 19.178,00    959.754,00
4   64.672,00 16.283,79 20.137,26 0,00 6.082,00 11.684,00 20.752,00  1.030.826,00
5   23.310,00 16.042,62 18.347,13 0,00 6.035,25 11.742,50 20.579,75    486.072,00
6    5.813,00 16.516,71 20.698,29 0,00 5.812,00 11.339,00 21.237,00    577.211,00

revol_util
count  mean   std  min   25%   50%   75%    max
0  135.092,00 40,04 23,00 0,00 22,17 38,10 56,20 146,10
1  238.176,00 52,01 23,15 0,00 35,10 52,30 69,50 892,30
2  228.323,00 56,52 23,22 0,00 39,90 57,60 74,40 366,60
3  129.937,00 59,18 23,39 0,00 42,80 60,90 77,60 193,00
4   64.672,00 60,11 23,82 0,00 43,60 62,30 78,90 156,30
5   23.310,00 61,03 24,46 0,00 44,10 63,60 80,90 136,00
6    5.813,00 60,92 25,04 0,00 43,70 63,70 81,20 153,00

tot_cur_bal
count       mean        std  min       25%        50%        75%            max
0  135.092,00 157.299,84 174.529,20 0,00 24.203,75 105.486,00 242.565,00   3.881.449,00
1  238.176,00 127.277,08 152.629,07 0,00 21.047,75  63.169,00 195.421,50   3.652.389,00
2  228.323,00 122.378,55 147.564,06 0,00 22.944,00  60.205,00 185.106,50   8.000.078,00
3  129.937,00 115.692,74 141.657,44 0,00 22.210,00  55.431,00 173.934,00   3.796.811,00
4   64.672,00 120.542,52 142.995,19 0,00 24.862,25  61.181,50 180.686,00   2.714.693,00
5   23.310,00 118.081,40 139.583,09 0,00 25.663,50  59.482,00 177.126,50   2.487.418,00
6    5.813,00 118.498,31 138.019,08 0,00 26.481,00  62.299,00 178.908,00   2.126.295,00

dti
count  mean  std   min   25%   50%   75%    max
0 135.092,00 15,29 7,43  0,00  9,74 14,77 20,41 215,88
1 238.176,00 17,05 8,33 -1,00 11,20 16,60 22,51 999,00
2 228.323,00 18,52 8,68  0,00 12,33 18,11 24,34 999,00
3 129.937,00 19,62 8,93  0,00 13,05 19,23 25,94 380,53
4  64.672,00 20,34 9,42  0,00 13,74 20,03 26,72 641,36
5  23.310,00 20,43 8,91  0,00 13,99 20,18 26,74  68,56
6   5.813,00 20,40 9,07  0,00 13,84 20,21 26,72 107,55

total_acc
count  mean   std  min   25%   50%   75%    max
1 135.092,00 26,86 11,60 2,00 18,00 25,00 34,00 133,00
2 238.176,00 25,34 11,67 2,00 17,00 24,00 32,00 150,00
3 228.323,00 24,98 11,96 2,00 16,00 23,00 32,00 151,00
4 129.937,00 24,73 12,25 2,00 16,00 23,00 32,00 176,00
5  64.672,00 25,44 12,50 2,00 16,00 24,00 32,00 162,00
6  23.310,00 25,51 12,71 2,00 16,00 24,00 33,00 113,00
7   5.813,00 25,55 12,83 3,00 16,00 24,00 33,00  99,00
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

	static String EXECUTION_TYPE = "AI";
//	static String EXECUTION_TYPE = "FIXED";
	
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
//				break;
			}
			
			if(EXECUTION_TYPE.equals("FIXED"))			
				historicManager.saveAllToFile("result_fixed.csv");
			else
				historicManager.saveAllToFile("result_ai.csv");
			
			historicManager.resetAllHistory();

//			break;
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
