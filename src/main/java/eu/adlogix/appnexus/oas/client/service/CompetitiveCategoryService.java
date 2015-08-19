package eu.adlogix.appnexus.oas.client.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import eu.adlogix.appnexus.oas.client.domain.CompetitiveCategory;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;

/**
 * Service to communicate with OAS Competitive Category
 */
public class CompetitiveCategoryService extends AbstractOasService {

	private final XmlRequestGenerator listCompetitiveCategoriesGenerator = new XmlRequestGenerator("list-competitive-categories");
	private final XmlRequestGenerator addCompetitiveCategoryGenerator = new XmlRequestGenerator("add-competitive-category");

	protected CompetitiveCategoryService(OasApiService apiService) {
		super(apiService);
	}

	/**
	 * Gets all Competitive Categories
	 * 
	 * @return The full {@link CompetitiveCategory} {@link List}
	 */
	public final List<CompetitiveCategory> getAll() {

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

	/**
	 * Add a Competitive Category if it doesn't exist
	 * 
	 * @param competitiveCategory
	 *            The {@link CompetitiveCategory} to create
	 */
	public final void add(final CompetitiveCategory competitiveCategory) {

		checkNotNull(competitiveCategory, "competitiveCategory");
		checkNotEmpty(competitiveCategory.getId(), "competitiveCategory ID");

		@SuppressWarnings("serial")
		final Map<String, Object> requestParameters = new HashMap<String, Object>() {
			{
				put("competitiveCategoryId", competitiveCategory.getId());
			}
		};

		performRequest(addCompetitiveCategoryGenerator, requestParameters);
	}
}
