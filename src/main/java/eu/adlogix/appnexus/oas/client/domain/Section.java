package eu.adlogix.appnexus.oas.client.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Refers to a Section in OAS
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
