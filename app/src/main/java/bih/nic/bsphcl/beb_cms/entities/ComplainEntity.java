package bih.nic.bsphcl.beb_cms.entities;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ComplainEntity implements Comparable<ComplainEntity>, Serializable {
    private String ComplaintNo;
    private String ComplaintType;
    private String ConatactNo;
    private String LastUpdate;
    private String ComplaintTime;
    private String LastPlannerGroup;
    private String Name;
    private String Consumer_Id;
    private String EscalationLevel;


    public String getComplaintNo() {
        return ComplaintNo;
    }

    public void setComplaintNo(String complaintNo) {
        ComplaintNo = complaintNo;
    }

    public String getComplaintType() {
        return ComplaintType;
    }

    public void setComplaintType(String complaintType) {
        ComplaintType = complaintType;
    }

    public String getConatactNo() {
        return ConatactNo;
    }

    public void setConatactNo(String conatactNo) {
        ConatactNo = conatactNo;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public String getComplaintTime() {
        return ComplaintTime;
    }

    public void setComplaintTime(String complaintTime) {
        ComplaintTime = complaintTime;
    }

    public String getLastPlannerGroup() {
        return LastPlannerGroup;
    }

    public void setLastPlannerGroup(String lastPlannerGroup) {
        LastPlannerGroup = lastPlannerGroup;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getConsumer_Id() {
        return Consumer_Id;
    }

    public void setConsumer_Id(String consumer_Id) {
        Consumer_Id = consumer_Id;
    }

    @Override
    public int compareTo(ComplainEntity o) {
        int get=0;
        try {
            //get= Long.parseLong(getComplaintTime()).compareTo(Long.parseLong(o.getComplaintTime()));
            get= getComplaintTime().compareTo(o.getComplaintTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return get;
    }

    public String getEscalationLevel() {
        return EscalationLevel;
    }

    public void setEscalationLevel(String escalationLevel) {
        EscalationLevel = escalationLevel;
    }
}
