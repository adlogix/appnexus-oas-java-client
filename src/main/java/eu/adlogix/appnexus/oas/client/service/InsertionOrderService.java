package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import eu.adlogix.appnexus.oas.client.domain.InsertionOrder;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

/**
 * Service Class which provides functions for all Insertion Order related operations
 * 
 */
@SuppressWarnings("serial")
public class InsertionOrderService extends AbstractOasService{

	private final XmlRequestGenerator readInsertionOrderRequestGenerator = new XmlRequestGenerator("read-insertion-order");

	private final XmlRequestGenerator addInsertionOrderRequestGenerator = new XmlRequestGenerator("add-insertion-order");

	private final XmlRequestGenerator updateInsertionOrderRequestGenerator = new XmlRequestGenerator("update-insertion-order");

	InsertionOrderService(OasApiService apiService) {
		super(apiService);
	}

	/**
	 * Adds a new {@link InsertionOrder}
	 * 
	 * @param insertionOrder
	 *            {@link InsertionOrder}
	 * @return
	 */
	public final void addInsertionOrder(final InsertionOrder insertionOrder) {

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
		insertionOrder.resetModifiedFlags();
	}

	/**
	 * Updates an existing {@link InsertionOrder}
	 * 
	 * @param insertionOrder
	 *            {@link InsertionOrder}
	 * @return
	 */
	public final void updateInsertionOrder(final InsertionOrder insertionOrder) {

		checkNotEmpty(insertionOrder.getId(), "insertionOrderId");

		final InsertionOrder modifiedInsertionOrder = insertionOrder.getInsertionOrderWithModifiedAttributes();

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
		insertionOrder.resetModifiedFlags();
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
	public InsertionOrder getInsertionOrderById(final String id) {

		checkNotEmpty(id, "insertionOrderId");

		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("insertionOrderId", id);
			}
		};

		final ResponseParser responseParser = performRequest(readInsertionOrderRequestGenerator, parameters);

		InsertionOrder insertionOrder = parseAndCreateInsertionOrder(responseParser);
		insertionOrder.resetModifiedFlags();
		return insertionOrder;
	}

	private InsertionOrder setDefaultsForEmptyFields(InsertionOrder insertionOrder) {
		insertionOrder.setCampaignsBy(defaultIfEmpty(insertionOrder.getCampaignsBy(), "A"));
		insertionOrder.setStatus(defaultIfEmpty(insertionOrder.getStatus(), "P"));
		insertionOrder.setInternalQuickReport(defaultIfEmpty(insertionOrder.getInternalQuickReport(), "short"));
		insertionOrder.setExternalQuickReport(defaultIfEmpty(insertionOrder.getExternalQuickReport(), "to-date"));
		insertionOrder.setAgencyId(defaultIfEmpty(insertionOrder.getAgencyId(), "unknown_agency"));
		return insertionOrder;

	}

	/**
	 * Creates an {@link InsertionOrder} object from the {@link ResponseParser}
	 * 
	 * @param parser
	 * @return created {@link InsertionOrder} object
	 */
	private InsertionOrder parseAndCreateInsertionOrder(ResponseParser parser) {
		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId(parser.getTrimmedElement("//InsertionOrder/Id"));
		insertionOrder.setDescription(parser.getTrimmedElement("//InsertionOrder/Description"));
		insertionOrder.setCampaignsBy(parser.getTrimmedElement("//InsertionOrder/CampaignsBy"));
		insertionOrder.setAdvertiserId(parser.getTrimmedElement("//InsertionOrder/AdvertiserId"));
		insertionOrder.setAgencyId(parser.getTrimmedElement("//InsertionOrder/AgencyId"));
		insertionOrder.setStatus(parser.getTrimmedElement("//InsertionOrder/Status"));
		String bookedImps = parser.getTrimmedElement("//InsertionOrder/BookedImpressions");
		if (!StringUtils.isEmpty(bookedImps)) {
			insertionOrder.setBookedImpressions(Long.valueOf(bookedImps));
		}
		String bookedClicks = parser.getTrimmedElement("//InsertionOrder/BookedClicks");
		if (!StringUtils.isEmpty(bookedClicks)) {
			insertionOrder.setBookedClicks(Long.valueOf(bookedClicks));
		}
		insertionOrder.setCampaignIds(parser.getTrimmedElementList("//InsertionOrder/Campaigns/CampaignId"));
		insertionOrder.setInternalQuickReport(parser.getTrimmedElement("//InsertionOrder/InternalQuickReport"));
		insertionOrder.setExternalQuickReport(parser.getTrimmedElement("//InsertionOrder/ExternalQuickReport"));
		return insertionOrder;
	}

}
