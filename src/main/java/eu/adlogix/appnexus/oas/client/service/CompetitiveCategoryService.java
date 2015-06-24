package eu.adlogix.appnexus.oas.client.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.collections.Lists;

import eu.adlogix.appnexus.oas.client.domain.CompetitiveCategory;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

public class CompetitiveCategoryService extends AbstractOasService {

	protected CompetitiveCategoryService(OasApiService apiService) {
		super(apiService);
	}

	private final XmlRequestGenerator listCompetitiveCategoriesGenerator = new XmlRequestGenerator("list-competitive-categories");
	private final XmlRequestGenerator addCompetitiveCategoryGenerator = new XmlRequestGenerator("add-competitive-category");

	public final List<CompetitiveCategory> getAllCompetitiveCategories() {

		@SuppressWarnings("serial")
		final Map<String, Object> requestParameters = new HashMap<String, Object>() {
			{
				put("competitiveCategoryId", "%");
			}
		};

		final List<CompetitiveCategory> categoryList = Lists.newArrayList();

		final ResponseParser parser = performRequest(listCompetitiveCategoriesGenerator, requestParameters);
		parser.forEachElement("//List/CompetitiveCategory", new ResponseElementHandler() {

			@Override
			public void processElement(ResponseElement element) {
				categoryList.add(new CompetitiveCategory(element.getChild("Id")));
			}
		});
		return categoryList;

	}
}
