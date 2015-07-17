package eu.adlogix.appnexus.oas.client.parser;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.InsertionOrder;
import eu.adlogix.appnexus.oas.client.domain.enums.CampaignsBy;
import eu.adlogix.appnexus.oas.client.domain.enums.InsertionOrderStatus;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;

import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createLong;

@AllArgsConstructor
public class XmlToInsertionOrderParser implements XmlToObjectParser<InsertionOrder> {

	private final ResponseParser parser;

	@Override
	public InsertionOrder parse() {

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId(parser.getTrimmedElement("//InsertionOrder/Id"));
		insertionOrder.setDescription(parser.getTrimmedElement("//InsertionOrder/Description"));
		insertionOrder.setCampaignsBy(CampaignsBy.fromString(parser.getTrimmedElement("//InsertionOrder/CampaignsBy")));
		insertionOrder.setAdvertiserId(parser.getTrimmedElement("//InsertionOrder/AdvertiserId"));
		insertionOrder.setAgencyId(parser.getTrimmedElement("//InsertionOrder/AgencyId"));
		insertionOrder.setStatus(InsertionOrderStatus.fromString(parser.getTrimmedElement("//InsertionOrder/Status")));
		insertionOrder.setBookedImpressions(createLong(parser.getTrimmedElement("//InsertionOrder/BookedImpressions")));
		insertionOrder.setBookedClicks(createLong(parser.getTrimmedElement("//InsertionOrder/BookedClicks")));
		insertionOrder.setCampaignIds(parser.getTrimmedElementList("//InsertionOrder/Campaigns/CampaignId"));
		insertionOrder.setInternalQuickReport(parser.getTrimmedElement("//InsertionOrder/InternalQuickReport"));
		insertionOrder.setExternalQuickReport(parser.getTrimmedElement("//InsertionOrder/ExternalQuickReport"));
		return insertionOrder;
	}

}
