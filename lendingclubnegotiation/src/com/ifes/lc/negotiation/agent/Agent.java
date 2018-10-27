package com.ifes.lc.negotiation.agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ifes.lc.negotiation.attribute.Attribute;
import com.ifes.lc.negotiation.attribute.AttributeProposal;
import com.ifes.lc.negotiation.attribute.AttributeType;
import com.ifes.lc.negotiation.attribute.Item;
import com.ifes.lc.negotiation.exception.NegotiationUnavailableException;
import com.ifes.lc.negotiation.history.HistoryManager;
import com.ifes.lc.negotiation.history.NegotiationHistory;
import com.ifes.lc.negotiation.info.Information;
import com.ifes.lc.negotiation.main.Application;
import com.ifes.lc.negotiation.strategie.NegotiationStrategie;
import com.ifes.lc.negotiation.util.Constants;
import com.ifes.lc.negotiation.util.Log;
import com.ifes.lc.negotiation.util.Util;

/**
 * Created by diegosoaresub on 28/05/17.
 */
public abstract class Agent {

	protected NegotiationStrategie strategie;

	private String name;

	protected Item obj;

	private Map<String, AttributeProposal> actualProposal;

	private Map<String, List<Double>> bidHistory;

	protected Map<String, Information> info;
	
	protected HistoryManager historyManager = HistoryManager.getInstance();
	
	public Agent(String name, Item obj, NegotiationStrategie strategie) {
		this.name = name;
		this.obj = obj;
		this.bidHistory = new HashMap<String, List<Double>>();
		this.strategie = strategie;
		this.info = new HashMap<>();
	}

	public Agent(String name, Item obj, NegotiationStrategie strategie, Map<String, Information> info) {
		this.name = name;
		this.obj = obj;
		this.bidHistory = new HashMap<String, List<Double>>();
		this.strategie = strategie;
		this.info = info;
	}
	
	public void makeProposal(Map<String, Information> opponentInfo) throws NegotiationUnavailableException {
		Map<String, AttributeProposal> proposal;

		if (Application.t == 1) {
			Log.println("\n\t[" + this.getName() + "] faz sua proposta inicial");

			proposal = this.strategie.initialProposal(this, opponentInfo);
			saveInitialValues(proposal);
			
		} else {
			Log.println("\n\t[" + this.getName() + "] faz uma proposta");
			proposal = this.strategie.proposal(this, this.getActualProposal(), opponentInfo);
		}

		this.setActualProposal(proposal);
		this.getActualProposal().forEach((k, v) -> Log.println("\t\t" + k + " = " + v.getValue()));
	}

	public boolean evaluateOnlyCounterProposal(Map<String, AttributeProposal> proposal,
											   Map<String, Information> opponentInfo) {
		return false;
	}
	
	
	public boolean evaluateProposal(Agent agentCounterProposal) {

		Double proposalValue = this.strategie.computeProposalScore(this, this.getActualProposal());
		Log.println("\n\t\tScore da Proposta = " + proposalValue);

		Log.println("\n\t[" + this.getName() + "] avalia a proposa de [" + agentCounterProposal.getName() + "]");

		Double counterProposalValue = this.strategie.computeCounterProposalScore(this, agentCounterProposal);
		Log.println("\n\t\tScore da Contraproposta = " + counterProposalValue);

		if (counterProposalValue >= proposalValue) {
			Log.println("\n\tContraproposta do [" + agentCounterProposal.getName() + "][" + counterProposalValue +"] aceita pelo ["
					+ this.getName() + "][" + proposalValue + "]\n");
			return true;
		} else
			Log.println("\n\tContraproposta do [" + agentCounterProposal.getName() + "][" + counterProposalValue +"] nao aceita pelo ["
					+ this.getName() + "][" + proposalValue + "]\n");

		return false;
	}

	// Deve ser chamado a cada rodada para armazenar os valores ofertados
	protected void saveRound() {
		if (bidHistory.isEmpty())
			actualProposal.forEach((k, v) -> bidHistory.put(k, new ArrayList<>()));

		actualProposal.forEach((k, v) -> bidHistory.get(k).add(v.getValue()));
	}

	public Map<String, AttributeProposal> getActualProposal() {
		return actualProposal;
	}

	protected void saveInitialValues(Map<String, AttributeProposal> proposal) {
		List<Attribute> attrs = obj.getAttrs();

		for (Attribute attribute : attrs) {
			Double vlr;
			if (attribute.getAttributeType().equals(AttributeType.BENEFIT))
				vlr = attribute.getMax();
			else
				vlr = attribute.getMin();

			bidHistory.put(attribute.getName(), new ArrayList<>());
			bidHistory.get(attribute.getName()).add(vlr);
		}
		
		Double installments = proposal.get(Constants.PARCELA).getValue();
		Double interestRate = proposal.get(Constants.JUROS).getValue();
		Double amount = proposal.get(Constants.QUANTIA).getValue();
		
		NegotiationHistory history = historyManager.getCurrentHistory();
		if(this.name.equals(Constants.BANK)) {
			history.setFirstBankInstallmentsProposal(installments == null ? 0 : installments);
			history.setFirstBankIntRateProposal(interestRate == null ? 0 : interestRate);
			history.setFirstBankAmountProposal(amount == null ? 0 : amount);
		}else {
			history.setFirstCustomerInstallmentsProposal(installments == null ? 0 : installments);
			history.setFirstCustomerIntRateProposal(interestRate == null ? 0 : interestRate);
			history.setFirstCustomerAmountProposal(amount == null ? 0 : amount);
		}
	}

	// Deve-se salvar a proposta atual apenas usando o set, para que o historico
	// possa ser salvo
	protected void setActualProposal(Map<String, AttributeProposal> actualProposal) {
		this.actualProposal = actualProposal;
		saveRound();
	}

	public void printHistory() {
		String str = "";
		String strCrescimento = "";
		String strCrescimentoTot = "\tTot.: ";

		Log.println(1, "\n\t" + this.getName());

		// Imprime o histórico
		for (Entry<String, List<Double>> entry : getBidHistory().entrySet()) {
			boolean first = true;

			str += "\t\t" + entry.getKey() + " = [ ";

			strCrescimento += "\t\t" + entry.getKey() + " = [ ";

			strCrescimentoTot += "\t" + entry.getKey() + " = ";

			// Calcula o crescimento
			Double[] vlrs = Arrays.copyOf(entry.getValue().toArray(), entry.getValue().toArray().length,
					Double[].class);
			for (int i = 1; i < vlrs.length; i++) {
				Double dValue = vlrs[i];
				Double dValuePrior = vlrs[i - 1];

				if (first) {
					first = false;
					str += "ini=[" + dValuePrior + "] " + dValue + " ";
				} else
					str += dValue + " ";

				strCrescimento += Util.round((((dValue - dValuePrior) / dValuePrior) * 100.0)) + " ";
			}
			str += "]";
			strCrescimento += "]";
			strCrescimentoTot += Util.round(vlrs[vlrs.length - 1] - vlrs[0]) + " => "
					+ Util.round((((vlrs[vlrs.length - 1] - vlrs[0]) / vlrs[0]) * 100.0)) + "% ";
		}
		Log.println(1, str);
		Log.println(1, "\n" + strCrescimento);
		Log.println(1, "\n\t" + strCrescimentoTot);
	}

	public void printProposal() {
		getActualProposal().forEach((k, v) -> {
			Log.print(1, k + ": " + v.getValue() + " ");
		});
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Item getObj() {
		return obj;
	}

	public void setObj(Item obj) {
		this.obj = obj;
	}

	public Map<String, List<Double>> getBidHistory() {
		return bidHistory;
	}

	public void setBidHistory(Map<String, List<Double>> bidHistory) {
		this.bidHistory = bidHistory;
	}

	public Map<String, Information> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Information> info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "Agent{" + "strategie=" + strategie + ", name='" + name + '\'' + ", obj=" + obj + ", actualProposal="
				+ actualProposal + '}';
	}

	public abstract Double evaluateProposalInfo(Double proposalValue, Map<String, AttributeProposal> map, Map<String, Information> info2);
}
