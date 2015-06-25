package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Defines a Companion Position in OAS
 */
@Data
@AllArgsConstructor
public class CompanionPosition {
	/**
	 * Identifies a {@link CompanionPosition}. Is in the form of 'T/L/M/R1/R2'
	 * where T,L,M,R1,R2 are short names of Positions
	 */
	private final String positionShortname;
}
