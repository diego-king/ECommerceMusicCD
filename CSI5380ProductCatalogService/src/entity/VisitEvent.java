package entity;

public class VisitEvent {
	private String day;
	private String cdid;
	private String eventtype;
	
	public String getEventtype() {
		return eventtype;
	}
	
	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}

	public String getCdid() {
		return cdid;
	}

	public void setCdid(String cdid) {
		this.cdid = cdid;
	}
}
