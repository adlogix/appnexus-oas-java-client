package eu.adlogix.appnexus.oas.client.domain;


public enum TargetGroup {
	GENERAL, ZONE, MOBILE, RDB, SEGMENT;

	public static TargetGroup getDefault() {
		return GENERAL;
	}
}
