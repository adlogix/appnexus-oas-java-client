package eu.adlogix.appnexus.oas.client.service.impl;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

import java.util.HashMap;
import java.util.Map;

import eu.adlogix.appnexus.oas.client.domain.InsertionOrder;
import eu.adlogix.appnexus.oas.client.domain.StatefulDomainManager;
import eu.adlogix.appnexus.oas.client.domain.enums.CampaignsBy;
import eu.adlogix.appnexus.oas.client.domain.enums.InsertionOrderStatus;
import eu.adlogix.appnexus.oas.client.parser.XmlToInsertionOrderParser;
import eu.adlogix.appnexus.oas.client.service.AbstractOasService;
import eu.adlogix.appnexus.oas.client.service.InsertionOrderService;
import eu.adlogix.appnexus.oas.client.service.OasApiService;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

/**
 * Default implementation of {@link InsertionOrderService} which provides
 * functions for Insertion Order related operations
 * 
 */
@SuppressWarnings("serial")
public class DefaultInsertionOrderService extends AbstractOasService implements InsertionOrderService {

	private final XmlRequestGenerator readInsertionOrderRequestGenerator = new XmlRequestGenerator("read-insertion-order");

	private final XmlRequestGenerator addInsertionOrderRequestGenerator = new XmlRequestGenerator("add-insertion-order");

	private final XmlRequestGenerator updateInsertionOrderRequestGenerator = new XmlRequestGenerator("update-insertion-order");

	StatefulDomainManager statefulObjectManager = new StatefulDomainManager();

	public DefaultInsertionOrderService(OasApiService apiService) {
		super(apiService);
	}

	/**
	 * Adds a new {@link InsertionOrder}
	 * 
	 * @param insertionOrder
	 *            {@link InsertionOrder}
	 * @return
	 */
	public final void add(final InsertionOrder insertionOrder) {

		checkNotEmpty(insertionOrder.getId(), "insertionOrderId");
		checkNotEmpty(insertionOrder.getAdvertiserId(), "advertiserId");

		final InsertionOrder insertionOrderWithDefValues = setDefaultsForEmptyFields(insertionOrder);

		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("insertionOrderId", insertionOrderWithDefValues.getId());
				put("insertionOrderDescription", insertionOrderWithDefValues.getDescription());
				put("campaignsBy", insertionOrderWithDefValues.getCampaignsBy());
				put("advertiserId", insertionOrderWithDefValues.getAdvertiserId());
				put("agencyId", insertionOrderWithDefValues.getAgencyId());
				put("status", insertionOrderWithDefValues.getStatus());
				put("bookedImpressions", insertionOrderWithDefValues.getBookedImpressions());
				put("bookedClicks", insertionOrderWithDefValues.getBookedClicks());
				put("campaignIds", (insertionOrderWithDefValues.getCampaignIds()));
				put("internalQuickReport", insertionOrderWithDefValues.getInternalQuickReport());
				put("externalQuickReport", insertionOrderWithDefValues.getExternalQuickReport());

			}
		};


		performRequest(addInsertionOrderRequestGenerator, parameters);
		insertionOrder.resetModifiedAttributes();
	}

	/**
	 * Updates an existing {@link InsertionOrder}
	 * 
	 * @param insertionOrder
	 *            {@link InsertionOrder}
	 * @return
	 */
	public final void update(final InsertionOrder insertionOrder) {

		checkNotEmpty(insertionOrder.getId(), "insertionOrderId");

		final InsertionOrder modifiedInsertionOrder = new StatefulDomainManager().getModifiedObject(insertionOrder);

		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("insertionOrderId", modifiedInsertionOrder.getId());
				put("insertionOrderDescription", modifiedInsertionOrder.getDescription());
				put("campaignsBy", modifiedInsertionOrder.getCampaignsBy());
				put("advertiserId", modifiedInsertionOrder.getAdvertiserId());
				put("agencyId", modifiedInsertionOrder.getAgencyId());
				put("status", modifiedInsertionOrder.getStatus());
				put("bookedImpressions", modifiedInsertionOrder.getBookedImpressions());
				put("bookedClicks", modifiedInsertionOrder.getBookedClicks());
				put("campaignIds", (modifiedInsertionOrder.getCampaignIds()));
				put("internalQuickReport", modifiedInsertionOrder.getInternalQuickReport());
				put("externalQuickReport", modifiedInsertionOrder.getExternalQuickReport());
			}
		};

		performRequest(updateInsertionOrderRequestGenerator, parameters);
		insertionOrder.resetModifiedAttributes();
	}

	/**
	 * Retrieves an existing {@link InsertionOrder} by the given insertion order
	 * id
	 * 
	 * @param id
	 *            OAS insertion order Id
	 * 
	 * @return {@link InsertionOrder}
	 * 
	 */
	public InsertionOrder getById(final String id) {

		checkNotEmpty(id, "insertionOrderId");

		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("insertionOrderId", id);
			}
		};

		final ResponseParser responseParser = performRequest(readInsertionOrderRequestGenerator, parameters);

		XmlToInsertionOrderParser insertionOrderParser = new XmlToInsertionOrderParser(responseParser);
		InsertionOrder insertionOrder = insertionOrderParser.parse();
		insertionOrder.resetModifiedAttributes();
		return insertionOrder;
	}

	private InsertionOrder setDefaultsForEmptyFields(InsertionOrder insertionOrder) {
		insertionOrder.setCampaignsBy(insertionOrder.getCampaignsBy() == null ? CampaignsBy.ADVERTISER
				: insertionOrder.getCampaignsBy());
		insertionOrder.setStatus(insertionOrder.getStatus() == null ? InsertionOrderStatus.PENDING
				: insertionOrder.getStatus());
		insertionOrder.setInternalQuickReport(defaultIfEmpty(insertionOrder.getInternalQuickReport(), "short"));
		insertionOrder.setExternalQuickReport(defaultIfEmpty(insertionOrder.getExternalQuickReport(), "to-date"));
		insertionOrder.setAgencyId(defaultIfEmpty(insertionOrder.getAgencyId(), "unknown_agency"));
		return insertionOrder;

	}



}
