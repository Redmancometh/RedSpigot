package net.mcavenue.redspigot.configuration.pojo;

import java.util.List;

/**
 * Data annotation removed due to extremely inconsistent packaging issues with
 * lombok. Really fucking annoying.
 * 
 * @author Redmancometh
 *
 */
public class SpringConfig {
	private List<String> profiles;

	public List<String> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<String> profiles) {
		this.profiles = profiles;
	}

}
