package com.ifes.lc.negotiation.history;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.ifes.lc.negotiation.csv.CsvBean;
import com.ifes.lc.negotiation.csv.CsvUtil;
import com.ifes.lc.negotiation.util.Log;

public class HistoryManager {

	private NegotiationHistory currentHistory;
	
	private List<CsvBean> histories = new ArrayList<>();

	private static HistoryManager instance;
	
	private HistoryManager() {}
	
	public static HistoryManager getInstance() {
		if(instance == null)
			instance = new HistoryManager();
		return instance;
	}
	
	public void saveCurrentHistory() {
		histories.add(currentHistory);
	}
	
	public NegotiationHistory newHistory() {
		currentHistory = new NegotiationHistory();
		return currentHistory;
	}
	
	public void resetAllHistory() {
		histories.clear();
	}
	
	public NegotiationHistory getCurrentHistory() {
		return currentHistory;
	}

	public void saveAllToFile(String fileName) {
		
		try {
			Path path = Paths.get("..", "history",  fileName);
			CsvUtil.writeCsvFromBean(path, histories);
			
		}catch(Exception ex) {
			Log.println(1, "ERROR: Could not save the historic file.");
			ex.printStackTrace();
		}
	}
}
