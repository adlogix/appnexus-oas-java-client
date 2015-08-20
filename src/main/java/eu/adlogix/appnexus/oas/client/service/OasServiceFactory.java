package eu.adlogix.appnexus.oas.client.service;

import eu.adlogix.appnexus.oas.client.domain.Credentials;

/**
 * Factory that should be used to create OAS Services ({@link DefaultAdvertiserService}
 * , {@link ReportService}, {@link NetworkService})
 * 
 **/
public class OasServiceFactory {
	
	private OasApiService oasApiService;

	private AdvertiserService advertiserService;

	private NetworkService networkService;

	private ReportService reportService;

	private InsertionOrderService insertionOrderService;

	private TargetingService targetingService;

	private CampaignService campaignService;

	private CompetitiveCategoryService competitiveCategoryService;

	private CampaignGroupService campaignGroupService;

	private CreativeService creativeService;

	private ProductService productService;


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
	 * Getter Method for {@link DefaultAdvertiserService}
	 * 
	 * @return {@link DefaultAdvertiserService}
	 */
	public AdvertiserService getAdvertiserService() {
		if (advertiserService == null) {
			advertiserService = new DefaultAdvertiserService(oasApiService);
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
			networkService = new DefaultNetworkService(oasApiService);
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
			reportService = new DefaultReportService(oasApiService);
		}
		return reportService;
	}

	/**
	 * Getter Method for {@link InsertionOrderService}
	 * 
	 * @return {@link InsertionOrderService}
	 */
	public InsertionOrderService getInsertionOrderService() {
		if (insertionOrderService == null) {
			insertionOrderService = new DefaultInsertionOrderService(oasApiService);
		}
		return insertionOrderService;
	}

	/**
	 * Getter Method for {@link TargetingService}
	 * 
	 * @return {@link TargetingService}
	 */
	public TargetingService getTargetingService() {
		if (targetingService == null) {
			targetingService = new DefaultTargetingService(oasApiService);
		}
		return targetingService;
	}

	/**
	 * Getter Method for {@link CompetitiveCategoryService}
	 * 
	 * @return {@link CompetitiveCategoryService}
	 */
	public CompetitiveCategoryService getCompetitiveCategoryService() {
		if (competitiveCategoryService == null) {
			competitiveCategoryService = new DefaultCompetitiveCategoryService(oasApiService);
		}
		return competitiveCategoryService;
	}

	/**
	 * Getter Method for {@link CampaignGroupService}
	 * 
	 * @return {@link CampaignGroupService}
	 */
	public CampaignGroupService getCampaignGroupService() {
		if (campaignGroupService == null) {
			campaignGroupService = new DefaultCampaignGroupService(oasApiService);
		}
		return campaignGroupService;
	}

	/**
	 * Getter Method for {@link CreativeService}
	 * 
	 * @return {@link CreativeService}
	 */
	public CreativeService getCreativeService() {
		if (creativeService == null) {
			creativeService = new DefaultCreativeService(oasApiService);
		}
		return creativeService;
	}
	/**
	 * Getter Method for {@link CampaignService}
	 * 
	 * @return {@link CampaignService}
	 */
	public CampaignService getCampaignService() {
		if (campaignService == null) {
			campaignService = new DefaultCampaignService(oasApiService);
		}
		return campaignService;
	}

	/**
	 * Getter Method for {@link ProductService}
	 * 
	 * @return {@link ProductService}
	 */
	public ProductService getProductService() {
		if (productService == null) {
			productService = new DefaultProductService(oasApiService);
		}
		return productService;
	}

}
