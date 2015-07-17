package eu.adlogix.appnexus.oas.client.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

/**
 * Represents a Page (Web page) which has a URL
 */
@Data
public final class Page {

	private final String url;
	private final List<Position> positions;
	private Site site;

	public Page(final String url, final List<Position> positions, final Site site) {
		this.url = url;
		this.site = site;
		if (positions == null) {
			this.positions = new ArrayList<Position>();
		} else {
			this.positions = Collections.unmodifiableList(positions);
		}
	}

	public Page(final String url, Site site) {
		super();
		this.url = url;
		this.site = site;
		this.positions = new ArrayList<Position>();
	}

	public Page(final String url) {
		super();
		this.url = url;
		this.positions = new ArrayList<Position>();
	}

	public void addPosition(Position position) {
		this.positions.add(position);
	}

}
