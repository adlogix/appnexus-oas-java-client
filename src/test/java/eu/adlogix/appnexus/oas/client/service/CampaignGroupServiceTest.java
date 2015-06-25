package eu.adlogix.appnexus.oas.client.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.rpc.ServiceException;

import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.domain.CampaignGroup;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.exceptions.ResourceNotFoundException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;
import eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CampaignGroupServiceTest {
	@Test
	public void createGroup_DoesntExist_Success() throws ServiceException, FileNotFoundException, URISyntaxException,
			IOException, ResourceNotFoundException {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignGroupService service = new CampaignGroupService(mockedApiService);

		final String request = contentFromFile("add-campaign-group.xml");
		when(mockedApiService.callApi(request, false)).thenReturn(contentFromFile("add-campaign-group-successful-response.xml"));

		service.createGroup(new CampaignGroup("testCampaignGrpGuni"));

		verify(mockedApiService).callApi(request, false);
	}

	private String contentFromFile(String fileName) throws FileNotFoundException, URISyntaxException, IOException,
			ResourceNotFoundException {
		return StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString(fileName, CampaignGroupServiceTest.class));
	}

	@Test(expectedExceptions = OasServerSideException.class, expectedExceptionsMessageRegExp = "OAS Error \\[512\\]: 'ID Already Exists.'")
	public void createGroup_AlreadyExists_Failure() throws FileNotFoundException, URISyntaxException, IOException,
			ResourceNotFoundException, ServiceException {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignGroupService service = new CampaignGroupService(mockedApiService);

		final String request = contentFromFile("add-campaign-group.xml");
		when(mockedApiService.callApi(request, false)).thenReturn(contentFromFile("add-campaign-group-id-already-exists-response.xml"));

		service.createGroup(new CampaignGroup("testCampaignGrpGuni"));

		verify(mockedApiService).callApi(request, false);
	}

	@Test(expectedExceptions = OasServerSideException.class)
	public void createGroup_Error_Failure() throws FileNotFoundException, URISyntaxException, IOException,
			ResourceNotFoundException, ServiceException {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignGroupService service = new CampaignGroupService(mockedApiService);

		final String request = contentFromFile("add-campaign-group.xml");
		when(mockedApiService.callApi(request, false)).thenReturn(contentFromFile("add-insertion-order-error.xml"));

		service.createGroup(new CampaignGroup("testCampaignGrpGuni"));
	}
}
