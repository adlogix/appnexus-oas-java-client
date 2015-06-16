package eu.adlogix.appnexus.oas.client.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Section {

	private String id;
	private List<Page> pages;

	public Section(String id) {
		super();
		this.id = id;
		this.pages = new ArrayList<Page>();
	}

}
