package eu.adlogix.appnexus.oas.client.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a Section which can group {@link Page}s based on various criteria.
 * Ex: based on the subject of its content
 */
@Data
@AllArgsConstructor
public class Section {

	private String id;
	private List<Page> pages;

	public Section(String id) {
		this.id = id;
		this.pages = new ArrayList<Page>();
	}

}
