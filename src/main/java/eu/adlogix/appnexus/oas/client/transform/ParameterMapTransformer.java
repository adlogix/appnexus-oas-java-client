package eu.adlogix.appnexus.oas.client.transform;

import java.util.Map;

public interface ParameterMapTransformer {

	public abstract Map<String, Object> transform();

}