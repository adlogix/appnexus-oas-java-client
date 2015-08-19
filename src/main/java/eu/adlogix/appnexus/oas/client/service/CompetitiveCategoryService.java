package eu.adlogix.appnexus.oas.client.service;

import java.util.List;

import eu.adlogix.appnexus.oas.client.domain.CompetitiveCategory;

/**
 * Service which provides functions for {@link CompetitiveCategory} related
 * operations
 */
public interface CompetitiveCategoryService {


	/**
	 * Gets all Competitive Categories
	 * 
	 * @return The full {@link CompetitiveCategory} {@link List}
	 */
	public List<CompetitiveCategory> getAll();

	/**
	 * Add a Competitive Category if it doesn't exist
	 * 
	 * @param competitiveCategory
	 *            The {@link CompetitiveCategory} to create
	 */
	public void add(final CompetitiveCategory competitiveCategory);

}
