package eu.adlogix.appnexus.oas.client.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.adlogix.appnexus.oas.client.domain.TargetingCode;
import eu.adlogix.appnexus.oas.client.domain.TargetingCodeData;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;

/**
 * Service to for OAS Targeting related activities
 */
public class TargetingService extends AbstractOasService {

	private final XmlRequestGenerator listCodesRequestGenerator = new XmlRequestGenerator("list-codes");

	protected TargetingService(OasApiService apiService) {
		super(apiService);
	}

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
