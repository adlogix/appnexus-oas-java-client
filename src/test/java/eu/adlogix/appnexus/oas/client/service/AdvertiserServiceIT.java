package eu.adlogix.appnexus.oas.client.service;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Date;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.client.util.Credentials;

public class AdvertiserServiceIT {

	AdvertiserService advertiserService = null;

	String advertiserId;

	@BeforeClass
	public void initialise() {
		Properties testCredentials = Credentials.loadTestCredentials();
		OasServiceFactory factory = new OasServiceFactory(testCredentials);
		advertiserService = factory.getAdvertiserService();
		String ts = Long.toString(new Date().getTime());
		advertiserId = "AdlogixAdvertiserTest_" + ts;

	}

	@Test
	public void addAdvertiser_ValidParameters_SuccessfullyAdd() {

		Advertiser advertiser = new Advertiser();
		advertiser.setId(advertiserId);
		advertiser.setOrganization("AdvertiserTest");
		advertiserService.addAdvertiser(advertiser);
		assertTrue(true);
	}

	@Test(dependsOnMethods = "addAdvertiser_ValidParameters_SuccessfullyAdd")
	public void getAdvertiserById_ExistingAdvertiser_ReturnAdvertiser() {

		Advertiser advertiser = advertiserService.getAdvertiserById(advertiserId);
		assertNotNull(advertiser);
	}

	@Test
	public void getAllAdvertisers_NoExceptions_ReturnAllAdvertisers() {
		advertiserService.getAllAdvertisers();
		assertTrue(true);

	}

	@Test(dependsOnMethods = "addAdvertiser_ValidParameters_SuccessfullyAdd")
	public void updateAdvertiser_ValidParameters_SuccessfullyUpdate() {

		Advertiser advertiser = new Advertiser();
		advertiser.setId(advertiserId);
		advertiser.setOrganization("AdvertiserTestOrganization");
		advertiserService.updateAdvertiser(advertiser);
		Advertiser updatedAdvertiser = advertiserService.getAdvertiserById(advertiserId);
		assertEquals(updatedAdvertiser.getOrganization(), "AdvertiserTestOrganization");
	}

}
