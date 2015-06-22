package eu.adlogix.appnexus.oas.client.service;

import eu.adlogix.appnexus.oas.client.domain.Credentials;
import eu.adlogix.appnexus.oas.client.domain.InsertionOrder;


/**
 * Factory that should be used to create OAS Services ({@link AdvertiserService}
 * , {@link ReportService}, {@link NetworkService})
 * 
 **/
public class OasServiceFactory {
	
	private OasApiService oasApiService;

	private AdvertiserService advertiserService;

	private NetworkService networkService;

	private ReportService reportService;

	private InsertionOrderService insertionOrderService;


	/**
	 * Initializes the OasServiceFactory with the provided {@link Credentials}
	 * 
	 * @param credentials
	 *            {@link Credentials} that should be used for accessing OAS API
	 * 
	 */
	public OasServiceFactory(final Credentials credentials) {

		this.oasApiService = new OasApiService(credentials);
	}

	/**
	 * Getter Method for {@link AdvertiserService}
	 * 
	 * @return {@link AdvertiserService}
	 */
	public AdvertiserService getAdvertiserService() {
		if (advertiserService == null) {
			advertiserService = new AdvertiserService(oasApiService);
		}
		return advertiserService;
	}

	/**
	 * Getter Method for {@link NetworkService}
	 * 
	 * @return {@link NetworkService}
	 */
	public NetworkService getNetworkService(){
		if (networkService == null) {
			networkService = new NetworkService(oasApiService);
		}
		return networkService;
	}

	/**
	 * Getter Method for {@link ReportService}
	 * 
	 * @return {@link ReportService}
	 */
	public ReportService getReportService() {
		if (reportService == null) {
			reportService = new ReportService(oasApiService);
		}
		return reportService;
	}

	/**
	 * Getter Method for {@link InsertionOrder}
	 * 
	 * @return {@link InsertionOrder}
	 */
	public InsertionOrderService getiInsertionOrderService() {
		if (insertionOrderService == null) {
			insertionOrderService = new InsertionOrderService(oasApiService);
		}
		return insertionOrderService;
	}

}
