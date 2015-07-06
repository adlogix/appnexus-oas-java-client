package eu.adlogix.appnexus.oas.client.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class StatefulDomainManagerTest {

	@Test
	public final void getModifiedObject_ObjectModified_ReturnObjectWithModifiedAttributes() {

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("insertion_order_1");
		insertionOrder.setDescription("test");
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_1", "campaign_2" }));
		InsertionOrder updatedInsertionOrder =new StatefulDomainManager().getModifiedObject(insertionOrder);
		assertEquals("insertion_order_1", updatedInsertionOrder.getId());
		assertEquals("test", updatedInsertionOrder.getDescription());
		assertEquals(2, updatedInsertionOrder.getCampaignIds().size());
	}

	@Test
	public final void getModifiedObject_ObjectResetAndModified_ReturnObjectWithModifiedAndPersistentAttributes() {

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("insertion_order_1");
		insertionOrder.setDescription("test");
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_1", "campaign_2" }));

		insertionOrder.resetModifiedAttributes();
		insertionOrder.setBookedClicks(1000l);

		InsertionOrder updatedInsertionOrder = new StatefulDomainManager().getModifiedObject(insertionOrder);
		assertEquals("insertion_order_1", updatedInsertionOrder.getId());
		assertEquals(null, updatedInsertionOrder.getDescription());
		assertEquals(null, updatedInsertionOrder.getCampaignIds());
		assertEquals(1000l, updatedInsertionOrder.getBookedClicks().longValue());
	}

	@Test
	public final void getModifiedObject_ObjectNotModified_ReturnEmptyObject() {

		InsertionOrder insertionOrder = new InsertionOrder();
		InsertionOrder updatedInsertionOrder = new StatefulDomainManager().getModifiedObject(insertionOrder);
		assertEquals(null, updatedInsertionOrder.getId());
		assertEquals(null, updatedInsertionOrder.getDescription());
		assertEquals(null, updatedInsertionOrder.getCampaignIds());

	}

	@Test
	public final void getModifiedObject_ObjectWithStatefulAttributes_ReturnObjectWithModifiedAttributes() {

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");
		campaign.setImpressions(1000l);
		campaign.setWeight("0");
		campaign.setPriorityLevel("0");
		campaign.setCompletion("S");
		campaign.setStartDate(new LocalDate(2010, 10, 1));
		campaign.setEndDate(new LocalDate(2010, 11, 2));

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setSegmentClusterMatch("L");
		segmentTargeting.setExclude(true);
		segmentTargeting.setValues(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }));
		campaign.setSegmentTargeting(segmentTargeting);

		Campaign updatedCampaign = new StatefulDomainManager().getModifiedObject(campaign);
		assertEquals("ADID", updatedCampaign.getId());
		assertEquals("W", updatedCampaign.getStatus());
		assertEquals(1000l, updatedCampaign.getImpressions().longValue());
		assertEquals("0", updatedCampaign.getWeight());
		assertEquals("0", updatedCampaign.getPriorityLevel());
		assertEquals(new LocalDate(2010, 10, 1), updatedCampaign.getStartDate());
		assertEquals(new LocalDate(2010, 11, 2), updatedCampaign.getEndDate());

		SegmentTargeting updatedSegmentTargeting = updatedCampaign.getSegmentTargeting();
		assertEquals("L", updatedSegmentTargeting.getSegmentClusterMatch());
		assertEquals(true, updatedSegmentTargeting.getExclude().booleanValue());
		assertEquals(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }), updatedSegmentTargeting.getValues());

	}

	@Test
	public final void getModifiedObject_ResetObjectWithStatefulAttributes_ReturnEmptyObject() {

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setSegmentClusterMatch("L");
		segmentTargeting.setExclude(true);
		segmentTargeting.setValues(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }));
		campaign.setSegmentTargeting(segmentTargeting);

		campaign.resetModifiedAttributes();

		Campaign updatedCampaign = new StatefulDomainManager().getModifiedObject(campaign);
		assertEquals("ADID", updatedCampaign.getId());
		assertEquals(null, updatedCampaign.getStatus());

		SegmentTargeting updatedSegmentTargeting = updatedCampaign.getSegmentTargeting();
		assertEquals(null, updatedSegmentTargeting);

	}

	@Test
	public final void getModifiedObject_ResetAndModifyObjectWithStatefulAttributes_ReturnModifiedObject() {

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setSegmentClusterMatch("L");
		segmentTargeting.setExclude(true);
		segmentTargeting.setValues(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }));
		campaign.setSegmentTargeting(segmentTargeting);

		campaign.resetModifiedAttributes();

		SegmentTargeting segmentTargetingAfterReset = campaign.getSegmentTargeting();
		segmentTargetingAfterReset.setSegmentClusterMatch("L");
		campaign.setSegmentTargeting(segmentTargetingAfterReset);

		Campaign updatedCampaign = new StatefulDomainManager().getModifiedObject(campaign);
		assertEquals("ADID", updatedCampaign.getId());
		assertEquals(null, updatedCampaign.getStatus());

		SegmentTargeting updatedSegmentTargeting = updatedCampaign.getSegmentTargeting();
		assertNotNull(updatedSegmentTargeting);
		assertEquals("L", updatedSegmentTargeting.getSegmentClusterMatch());
		assertEquals(null, updatedSegmentTargeting.getExclude());
		assertEquals(null, updatedSegmentTargeting.getValues());

	}

	@Test
	public final void getModifiedObject_ObjectWithStatefulList_ReturnModifiedObject() {

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");

		List<GeneralCampaignTargeting> targeting = new ArrayList<GeneralCampaignTargeting>();

		GeneralCampaignTargeting topLevelDomain = new GeneralCampaignTargeting(TargetingCode.TOP_DOMAIN);
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		targeting.add(topLevelDomain);

		GeneralCampaignTargeting bandwidthTargeting = new GeneralCampaignTargeting(TargetingCode.BANDWIDTH);
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		targeting.add(bandwidthTargeting);

		campaign.setTargetings(targeting);

		Campaign updatedCampaign = new StatefulDomainManager().getModifiedObject(campaign);
		assertEquals("ADID", updatedCampaign.getId());
		assertEquals("W", updatedCampaign.getStatus());

		List<GeneralCampaignTargeting> updatedTargeting = updatedCampaign.getTargetings();
		assertEquals(2, updatedTargeting.size());

		AbstractExcludableCampaignTargeting updatedTopLevelDomain = (AbstractExcludableCampaignTargeting) updatedTargeting.get(0);
		assertEquals(false, updatedTopLevelDomain.getExclude().booleanValue());
		assertEquals(Arrays.asList("US", "COM", "EDU"), updatedTopLevelDomain.getValues());

		AbstractExcludableCampaignTargeting updatedBandwidth = (AbstractExcludableCampaignTargeting) updatedTargeting.get(1);
		assertEquals(true, updatedBandwidth.getExclude().booleanValue());
		assertEquals(Arrays.asList("LAN", "DSL/Cable"), updatedBandwidth.getValues());

	}

	@Test
	public final void getModifiedObject_ResetObjectWithStatefulList_ReturnEmptyObject() {

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");

		List<GeneralCampaignTargeting> targeting = new ArrayList<GeneralCampaignTargeting>();

		GeneralCampaignTargeting topLevelDomain = new GeneralCampaignTargeting(TargetingCode.TOP_DOMAIN);
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		targeting.add(topLevelDomain);

		GeneralCampaignTargeting bandwidthTargeting = new GeneralCampaignTargeting(TargetingCode.BANDWIDTH);
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		targeting.add(bandwidthTargeting);

		campaign.setTargetings(targeting);

		campaign.resetModifiedAttributes();

		Campaign updatedCampaign = new StatefulDomainManager().getModifiedObject(campaign);
		assertEquals("ADID", updatedCampaign.getId());
		assertEquals(null, updatedCampaign.getStatus());

		List<GeneralCampaignTargeting> updatedTargeting = updatedCampaign.getTargetings();
		assertEquals(null, updatedTargeting);

	}

	@Test
	public final void getModifiedObject_ResetAndModifyObjectWithObjectWithStatefulList_ReturnModifiedObject() {

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");

		List<GeneralCampaignTargeting> targeting = new ArrayList<GeneralCampaignTargeting>();

		GeneralCampaignTargeting topLevelDomain = new GeneralCampaignTargeting(TargetingCode.TOP_DOMAIN);
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		targeting.add(topLevelDomain);

		GeneralCampaignTargeting bandwidthTargeting = new GeneralCampaignTargeting(TargetingCode.BANDWIDTH);
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		targeting.add(bandwidthTargeting);

		campaign.setTargetings(targeting);

		campaign.resetModifiedAttributes();

		GeneralCampaignTargeting countryTargeting = new GeneralCampaignTargeting(TargetingCode.COUNTRY);
		countryTargeting.setExclude(true);
		countryTargeting.setValues(Arrays.asList("BE"));

		List<GeneralCampaignTargeting> campaignTargeting = new ArrayList<GeneralCampaignTargeting>(campaign.getTargetings());
		campaignTargeting.add(countryTargeting);
		campaign.setTargetings(campaignTargeting);

		Campaign updatedCampaign = new StatefulDomainManager().getModifiedObject(campaign);
		assertEquals("ADID", updatedCampaign.getId());
		assertEquals(null, updatedCampaign.getStatus());

		List<GeneralCampaignTargeting> updatedTargeting = updatedCampaign.getTargetings();
		assertEquals(3, updatedTargeting.size());

	}


}
