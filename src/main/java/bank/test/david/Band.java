package bank.test.david;

import java.util.List;

public class Band {

	private String name;
	private String activeYears;
	private List<String> members;

	public Band() {
	}
	
	public Band(String name, String activeYears, List<String> members) {
		this.name = name;
		this.activeYears = activeYears;
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public String getActiveYears() {
		return activeYears;
	}
	
	public List<String> getMembers() {
		return members;
	}
}