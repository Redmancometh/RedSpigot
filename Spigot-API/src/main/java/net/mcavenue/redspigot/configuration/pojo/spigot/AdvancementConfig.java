package net.mcavenue.redspigot.configuration.pojo.spigot;

import java.util.List;
import lombok.Data;

@Data
public class AdvancementConfig {
	private boolean disableSaving;
	private List<String> disabled;
	public boolean isDisableSaving() {
		return disableSaving;
	}
	public void setDisableSaving(boolean disableSaving) {
		this.disableSaving = disableSaving;
	}
	public List<String> getDisabled() {
		return disabled;
	}
	public void setDisabled(List<String> disabled) {
		this.disabled = disabled;
	}

}
