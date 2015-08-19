package eu.adlogix.appnexus.oas.client.service;

import java.util.List;

import eu.adlogix.appnexus.oas.client.domain.TargetingCodeData;
import eu.adlogix.appnexus.oas.client.domain.enums.TargetingCode;

/**
 * Service which provides functions for OAS Targeting related activities
 */
public interface TargetingService {


	/**
	 * Gets possible values ({@link TargetingCodeData}) corresponding to a
	 * particular targeting type code ({@link TargetingCode}) where a
	 * TargetingCodeData value can be assigned to a Campaign upon
	 * creation/updating
	 * 
	 * @param targetingCode
	 *            {@link TargetingCode} corresponding to a type of targeting
	 *            which can be assigned to a Campaign upon creation/updating
	 * @return Possible values for the {@link TargetingCode}
	 */
	public List<TargetingCodeData> getTargetingCodeDataLists(final TargetingCode targetingCode);

}
