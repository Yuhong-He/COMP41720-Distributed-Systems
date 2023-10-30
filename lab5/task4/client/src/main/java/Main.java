import java.io.IOException;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import service.core.Application;
import service.core.ClientInfo;
import service.core.Quotation;

public class Main {
	
	/**
	 * This is the starting point for the application. Here, we must
	 * get a reference to the Broker Service and then invoke the
	 * getQuotations() method on that service.
	 * 
	 * Finally, you should print out all quotations returned
	 * by the service.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		String url = "http://localhost:8083/applications?urls=localhost:8080/quotations,localhost:8081/quotations,localhost:8082/quotations";

		// Create the broker and run the test data
		for (ClientInfo info : clients) {
			displayProfile(info);

			/* Use OkHttpClient */
			OkHttpClient client = new OkHttpClient.Builder()
					.readTimeout(1000, TimeUnit.MILLISECONDS)
					.writeTimeout(1000, TimeUnit.MILLISECONDS)
					.build();

			//1. Create JSON Request for sending in the POST method
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = objectMapper.writeValueAsString(info);

			//2. Create Request Body
			RequestBody body = RequestBody.create(
					requestBody,
					MediaType.parse("application/json"));

			//3. Create POST HTTP request
			Request postRequest = new Request.Builder()
					.url(url)
					.post(body)
					.build();

			//4. Synchronous call to the REST API
			Response response = client.newCall(postRequest).execute();
			assert response.body() != null;
			String responseBody = response.body().string();

			//5. Print quotations
			Application application = objectMapper.readValue(responseBody, Application.class);
			for(Quotation quotation: application.quotations) {
				displayQuotation(quotation);
			}

			// Print a couple of lines between each client
			System.out.println("\n");
		}
		System.exit(0);
	}
	
	/**
	 * Display the client info nicely.
	 * 
	 * @param info
	 */
	public static void displayProfile(ClientInfo info) {
		System.out.println("|=================================================================================================================|");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println(
				"| Name: " + String.format("%1$-29s", info.name) + 
				" | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
				" | Age: " + String.format("%1$-30s", info.age)+" |");
		System.out.println(
				"| Weight/Height: " + String.format("%1$-20s", info.weight+"kg/"+info.height+"m") + 
				" | Smoker: " + String.format("%1$-27s", info.smoker?"YES":"NO") +
				" | Medical Problems: " + String.format("%1$-17s", info.medicalIssues?"YES":"NO")+" |");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println("|=================================================================================================================|");
	}

	/**
	 * Display a quotation nicely - note that the assumption is that the quotation will follow
	 * immediately after the profile (so the top of the quotation box is missing).
	 * 
	 * @param quotation
	 */
	public static void displayQuotation(Quotation quotation) {
		System.out.println(
				"| Company: " + String.format("%1$-26s", quotation.company) + 
				" | Reference: " + String.format("%1$-24s", quotation.reference) +
				" | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
		System.out.println("|=================================================================================================================|");
	}
	
	/**
	 * Test Data
	 */
	public static final ClientInfo[] clients = {
		new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false),
		new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 1.6, 100, true, true),
		new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 21, 1.78, 65, false, false),
		new ClientInfo("Rem Collier", ClientInfo.MALE, 49, 1.8, 120, false, true),
		new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 1.9, 75, true, false),
		new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 0.45, 1.6, false, false)
	};
}
