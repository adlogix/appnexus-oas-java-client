package eu.adlogix.appnexus.oas.client.transformers;

import java.util.Map;

public interface ParameterMapTransformer {

	public abstract Map<String, Object> transform();

}