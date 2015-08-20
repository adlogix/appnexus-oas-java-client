package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.adlogix.appnexus.oas.client.domain.TargetingCodeData;
import eu.adlogix.appnexus.oas.client.domain.enums.TargetingCode;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

/**
 * Default implementation of {@link TargetingService} which provides functions
 * for Targeting related activities
 */
public class DefaultTargetingService extends AbstractOasService implements TargetingService {

	private final XmlRequestGenerator listCodesRequestGenerator = new XmlRequestGenerator("list-codes");

	public DefaultTargetingService(OasApiService apiService) {
		super(apiService);
	}

	@Override
	public List<TargetingCodeData> getTargetingCodeDataLists(final TargetingCode targetingCode) {

		checkNotNull(targetingCode, "targetingCode");

		final Map<String, Object> requestParameters = Maps.newHashMap();
		requestParameters.put("requestType", targetingCode);
		requestParameters.put("databaseAction", targetingCode.getDatabaseAction());

		final ResponseParser parser = performRequest(listCodesRequestGenerator, requestParameters);

		final List<TargetingCodeData> codeDatas = Lists.newArrayList();

		parser.forEachElement("//AdXML/Response/List/" + targetingCode, new ResponseElementHandler() {
			@Override
			public final void processElement(final ResponseElement element) {
				final String code = element.getChild("Code");
				final String name = element.getChild("Name");
				final String child = element.getChild("Description");
				final TargetingCodeData codeData = new TargetingCodeData(code, name, child);
				codeDatas.add(codeData);
			}
		});

		return codeDatas;
	}
}
