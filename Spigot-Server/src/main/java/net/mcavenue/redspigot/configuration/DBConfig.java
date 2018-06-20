package net.mcavenue.redspigot.configuration;
/**
 * Tired of these fucking lombok errors. Will remove setters and getters later.
 * 
 * @author Redmancometh
 *
 */
public class DBConfig {
	private String url, user, password;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
