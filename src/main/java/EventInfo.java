
import java.util.Date;
import java.sql.Time;

public class EventInfo {

	String eName;
	String eDesc;
	String eDate;
	String startTime;
    String endTime;
    String eventLocation;
	
	  public String geteName() {
        return eName;
    }

    public String geteDesc() {
        return eDesc;
    }

    public String geteDate() {
        return eDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public void seteDesc(String eDesc) {
        this.eDesc = eDesc;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}