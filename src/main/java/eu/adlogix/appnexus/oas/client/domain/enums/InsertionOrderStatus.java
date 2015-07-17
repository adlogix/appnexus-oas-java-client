package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.InsertionOrder;
import eu.adlogix.appnexus.oas.client.utils.EnumUtils;

/**
 * "Insertion Order Status" values which can be assigned to a
 * {@link InsertionOrder} in Insertion Order creation, update and retrieval. The
 * OAS code is accessible via {@link #toString()}
 */
@AllArgsConstructor
public enum InsertionOrderStatus implements ToStringReturnsEnumCode {

	APPROVED("A"), PENDING("P");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link InsertionOrderStatus} object which corresponds to the
	 * given OAS code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link InsertionOrderStatus} object
	 */
	public static InsertionOrderStatus fromString(String code) {
		return EnumUtils.fromString(code, values(), InsertionOrderStatus.class.getSimpleName());
	}

}
