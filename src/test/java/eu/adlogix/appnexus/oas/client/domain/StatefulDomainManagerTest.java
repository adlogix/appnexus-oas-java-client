package eu.adlogix.appnexus.oas.client.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.testng.annotations.Test;

import com.google.common.collect.Maps;

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
		campaign.setStatus(CampaignStatus.WORK_IN_PROGRESS);
		campaign.setImpressions(1000l);
		campaign.setWeight(0l);
		campaign.setPriorityLevel(0l);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setStartDate(new LocalDate(2010, 10, 1));
		campaign.setEndDate(new LocalDate(2010, 11, 2));

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setSegmentType(SegmentType.ALL);
		segmentTargeting.setExclude(true);
		segmentTargeting.setValues(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }));
		campaign.setSegmentTargeting(segmentTargeting);

		Campaign updatedCampaign = new StatefulDomainManager().getModifiedObject(campaign);
		assertEquals("ADID", updatedCampaign.getId());
		assertEquals(CampaignStatus.WORK_IN_PROGRESS, updatedCampaign.getStatus());
		assertEquals(1000l, updatedCampaign.getImpressions().longValue());
		assertEquals(0l, updatedCampaign.getWeight().longValue());
		assertEquals(0l, updatedCampaign.getPriorityLevel().longValue());
		assertEquals(new LocalDate(2010, 10, 1), updatedCampaign.getStartDate());
		assertEquals(new LocalDate(2010, 11, 2), updatedCampaign.getEndDate());

		SegmentTargeting updatedSegmentTargeting = updatedCampaign.getSegmentTargeting();
		assertEquals(SegmentType.ALL, updatedSegmentTargeting.getSegmentType());
		assertEquals(true, updatedSegmentTargeting.getExclude().booleanValue());
		assertEquals(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }), updatedSegmentTargeting.getValues());

	}

	@Test
	public final void getModifiedObject_ResetObjectWithStatefulAttributes_ReturnEmptyObject() {

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus(CampaignStatus.WORK_IN_PROGRESS);

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setSegmentType(SegmentType.ALL);
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
		campaign.setStatus(CampaignStatus.WORK_IN_PROGRESS);

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setSegmentType(SegmentType.ALL);
		segmentTargeting.setExclude(true);
		segmentTargeting.setValues(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }));
		campaign.setSegmentTargeting(segmentTargeting);

		campaign.resetModifiedAttributes();

		SegmentTargeting segmentTargetingAfterReset = campaign.getSegmentTargeting();
		segmentTargetingAfterReset.setSegmentType(SegmentType.ALL);
		campaign.setSegmentTargeting(segmentTargetingAfterReset);

		Campaign updatedCampaign = new StatefulDomainManager().getModifiedObject(campaign);
		assertEquals("ADID", updatedCampaign.getId());
		assertEquals(null, updatedCampaign.getStatus());

		SegmentTargeting updatedSegmentTargeting = updatedCampaign.getSegmentTargeting();
		assertNotNull(updatedSegmentTargeting);
		assertEquals(SegmentType.ALL, updatedSegmentTargeting.getSegmentType());
		assertEquals(null, updatedSegmentTargeting.getExclude());
		assertEquals(null, updatedSegmentTargeting.getValues());

	}

	@Test
	public final void getModifiedObject_ObjectWithStatefulMap_ReturnModifiedObject() {

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus(CampaignStatus.WORK_IN_PROGRESS);

		Map<TargetingCode, CampaignExcludableTargetValues> targeting = Maps.newHashMap();

		CampaignExcludableTargetValues topLevelDomain = new CampaignExcludableTargetValues();
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		targeting.put(TargetingCode.TOP_DOMAIN, topLevelDomain);

		CampaignExcludableTargetValues bandwidthTargeting = new CampaignExcludableTargetValues();
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		targeting.put(TargetingCode.BANDWIDTH, bandwidthTargeting);

		campaign.setTargetings(targeting);

		Campaign updatedCampaign = new StatefulDomainManager().getModifiedObject(campaign);
		assertEquals("ADID", updatedCampaign.getId());
		assertEquals(CampaignStatus.WORK_IN_PROGRESS, updatedCampaign.getStatus());

		Map<TargetingCode, CampaignExcludableTargetValues> updatedTargeting = updatedCampaign.getTargetings();
		assertEquals(2, updatedTargeting.size());

		CampaignExcludableTargetValues updatedTopLevelDomain = updatedTargeting.get(TargetingCode.TOP_DOMAIN);
		assertEquals(false, updatedTopLevelDomain.getExclude().booleanValue());
		assertEquals(Arrays.asList("US", "COM", "EDU"), updatedTopLevelDomain.getValues());

		CampaignExcludableTargetValues updatedBandwidth = updatedTargeting.get(TargetingCode.BANDWIDTH);
		assertEquals(true, updatedBandwidth.getExclude().booleanValue());
		assertEquals(Arrays.asList("LAN", "DSL/Cable"), updatedBandwidth.getValues());

	}

	@Test
	public final void getModifiedObject_ResetObjectWithStatefulMap_ReturnEmptyObject() {

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus(CampaignStatus.WORK_IN_PROGRESS);

		Map<TargetingCode, CampaignExcludableTargetValues> targeting = Maps.newHashMap();

		CampaignExcludableTargetValues topLevelDomain = new CampaignExcludableTargetValues();
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		targeting.put(TargetingCode.TOP_DOMAIN, topLevelDomain);

		CampaignExcludableTargetValues bandwidthTargeting = new CampaignExcludableTargetValues();
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		targeting.put(TargetingCode.BANDWIDTH, bandwidthTargeting);

		campaign.setTargetings(targeting);

		campaign.resetModifiedAttributes();

		Campaign updatedCampaign = new StatefulDomainManager().getModifiedObject(campaign);
		assertEquals("ADID", updatedCampaign.getId());
		assertEquals(null, updatedCampaign.getStatus());

		Map<TargetingCode, CampaignExcludableTargetValues> updatedTargeting = updatedCampaign.getTargetings();
		assertEquals(null, updatedTargeting);

	}

	@Test
	public final void getModifiedObject_ResetAndModifyObjectWithObjectWithStatefulMap_ReturnModifiedObject() {

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus(CampaignStatus.WORK_IN_PROGRESS);

		Map<TargetingCode, CampaignExcludableTargetValues> targeting = Maps.newHashMap();

		CampaignExcludableTargetValues topLevelDomain = new CampaignExcludableTargetValues();
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		targeting.put(TargetingCode.TOP_DOMAIN, topLevelDomain);

		CampaignExcludableTargetValues bandwidthTargeting = new CampaignExcludableTargetValues();
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		targeting.put(TargetingCode.BANDWIDTH, bandwidthTargeting);

		campaign.setTargetings(targeting);

		campaign.resetModifiedAttributes();

		CampaignExcludableTargetValues countryTargeting = new CampaignExcludableTargetValues();
		countryTargeting.setExclude(true);
		countryTargeting.setValues(Arrays.asList("BE"));

		Map<TargetingCode, CampaignExcludableTargetValues> campaignTargeting = new HashMap<TargetingCode, CampaignExcludableTargetValues>(campaign.getTargetings());
		campaignTargeting.put(TargetingCode.COUNTRY, countryTargeting);
		campaign.setTargetings(campaignTargeting);

		Campaign updatedCampaign = new StatefulDomainManager().getModifiedObject(campaign);
		assertEquals("ADID", updatedCampaign.getId());
		assertEquals(null, updatedCampaign.getStatus());

		Map<TargetingCode, CampaignExcludableTargetValues> updatedTargeting = updatedCampaign.getTargetings();
		assertEquals(3, updatedTargeting.size());

	}


}
