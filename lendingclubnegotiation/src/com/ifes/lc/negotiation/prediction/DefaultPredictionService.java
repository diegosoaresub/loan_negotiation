package com.ifes.lc.negotiation.prediction;

import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.ifes.lc.negotiation.util.Log;

public class DefaultPredictionService {
	private static final String REST_URI = "http://127.0.0.1:5000/";

	private final WebTarget webTarget;
	 
	private static DefaultPredictionService instance;

	public static DefaultPredictionService getInstance() {
		if(instance == null)
			instance = new DefaultPredictionService();
		return instance;
	}
	
    private DefaultPredictionService() {
        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.READ_TIMEOUT, 30000)
                .property(ClientProperties.CONNECT_TIMEOUT, 5000);
 
        webTarget = ClientBuilder
                .newClient(clientConfig)
                .target(REST_URI);
    }
    
	public CalculateLimitsResponse calculateFixedLimits(CalculateLimitsRequest params) {
		
		CalculateLimitsResponse result = webTarget
							.path("calculateFixedLimits")
							.request(MediaType.APPLICATION_JSON)
							.post(Entity.json(params))
							.readEntity(CalculateLimitsResponse.class);
		Log.println("\ncalculateLimits:");
		Log.println("\tInterest Rate: " + result.getMin_int_rate() + " - " + result.getMax_int_rate());
		Log.println("\tAmount       : " + result.getMin_amount()   + " - " + result.getMax_amount());
		return result;
	}

    
	public CalculateLimitsResponse calculateLimits(CalculateLimitsRequest params) {
		
		CalculateLimitsResponse result = webTarget
							.path("calculateLimits")
							.request(MediaType.APPLICATION_JSON)
							.post(Entity.json(params))
							.readEntity(CalculateLimitsResponse.class);
		Log.println("\ncalculateLimits:");
		Log.println("\tInterest Rate: " + result.getMin_int_rate() + " - " + result.getMax_int_rate());
		Log.println("\tAmount       : " + result.getMin_amount()   + " - " + result.getMax_amount());
		return result;
	}

	
	public DefaultPredictionResponse predict(Map params) {
		
		return webTarget
					.path("predictDefault")
					.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(params))
					.readEntity(DefaultPredictionResponse.class);
	}

	public NextProposalResponse nextProposal(Map<String, Object> params) {
		
		return 	webTarget
					.path("nextProposal")
					.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(params))
					.readEntity(NextProposalResponse.class);		
	}

}
