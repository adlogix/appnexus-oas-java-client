package eu.adlogix.appnexus.oas.client.service;

import java.util.List;

import org.joda.time.DateTime;

import eu.adlogix.appnexus.oas.client.domain.CompanionPosition;
import eu.adlogix.appnexus.oas.client.domain.Page;
import eu.adlogix.appnexus.oas.client.domain.Position;
import eu.adlogix.appnexus.oas.client.domain.Section;
import eu.adlogix.appnexus.oas.client.domain.Site;

/**
 * Service for Network related actions such as dealing with {@link Site}s,
 * {@link Page}s, {@link Section}s, {@link Position}s and
 * {@link CompanionPosition}s
 */
public interface NetworkService {

	/**
	 * Retrieve all sites
	 */
	public List<Site> getAllSites();

	/**
	 * Retrieve list of pages with page urls which are modified since the given
	 * last modified date. (eg Page url "www.test.com/home@Bottom")
	 * 
	 * @param lastModifiedDate
	 *            Used to retrieve all modifications since this given date. If
	 *            null, everything will be retrieved.
	 * @param allSites
	 *            {@link List} which contains all the OAS {@link Site}s mapped
	 *            against their IDs
	 * 
	 * @return
	 */
	public List<Page> getAllPagesModifiedSinceDate(final DateTime lastModifiedDate,
			final List<Site> allSites);

	/**
	 * Retrieve list of pages with page urls. (eg Page url
	 * "www.test.com/home@Bottom") No {@link Site} details are loaded since
	 * {@link Page#getSite()} only contains ID
	 * 
	 * @return
	 */
	public List<Page> getAllPagesWithoutSiteDetails();

	/**
	 * Retrieve list of pages with with page urls. (eg Page url
	 * "www.test.com/home@Bottom") which are modified since the given last
	 * modified date. No {@link Site} details are loaded since
	 * {@link Page#getSite()} only contains ID
	 * 
	 * @param lastModifiedDate
	 *            Used to retrieve all modifications since this given date. If
	 *            null, everything will be retrieved.
	 * @return
	 */
	public List<Page> getAllPagesWithoutSiteDetailsModifiedSinceDate(final DateTime lastModifiedDate);

	/**
	 * Retrieve full list of sections
	 * 
	 * @return
	 */
	public List<Section> getAllSections();

	/**
	 * Retrieve list of sections that are modified since the given last modified
	 * date
	 * 
	 * @param lastModifiedDate
	 *            Used to retrieve all modifications since this given date. If
	 *            null, everything will be retrieved.
	 * @return
	 */
	public List<Section> getSectionListModifiedSinceDate(DateTime lastModifiedDate);

	/**
	 * Retrieve a single section by id
	 * 
	 * @param sectionId
	 *            OAS ID of the section that needs to be retrieved
	 * @return
	 */
	public Section readSection(final String sectionId);

	/**
	 * Get Position By Name
	 * 
	 * @param positionName
	 *            Unique {@link Position#getName()}
	 * @return {@link Position}
	 */
	public Position getPositionByName(final String positionName);

	/**
	 * Get all {@link Position}s in the Network
	 * 
	 * @return {@link List} of {@link Position}s
	 */
	public List<Position> getAllPositions();


	/**
	 * Get all {@link CompanionPosition}s of OAS
	 * 
	 * @return all {@link CompanionPosition}s
	 */
	public List<CompanionPosition> getAllCompanionsPositions();
}
