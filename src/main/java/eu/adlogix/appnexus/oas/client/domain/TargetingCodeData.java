package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Possible values corresponding to a particular targeting type code
 * TargetingCode where a TargetingCodeData value can be assigned to a Campaign
 * upon creation/updating
 */
@AllArgsConstructor
@Data
public class TargetingCodeData {
	private final String code;
	private final String name;
	private final String description;
}
