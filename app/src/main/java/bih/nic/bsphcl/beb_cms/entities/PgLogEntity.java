package bih.nic.bsphcl.beb_cms.entities;

import java.io.Serializable;

public class PgLogEntity implements Serializable,Comparable<PgLogEntity> {
    private String id;
    private String pgLevel;
    private String status;
    private String remarks;
    private String reopenCount;
    private String allotmentTime;
    private String resolutionTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPgLevel() {
        return pgLevel;
    }

    public void setPgLevel(String pgLevel) {
        this.pgLevel = pgLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReopenCount() {
        return reopenCount;
    }

    public void setReopenCount(String reopenCount) {
        this.reopenCount = reopenCount;
    }

    public String getAllotmentTime() {
        return allotmentTime;
    }

    public void setAllotmentTime(String allotmentTime) {
        this.allotmentTime = allotmentTime;
    }

    public String getResolutionTime() {
        return resolutionTime;
    }

    public void setResolutionTime(String resolutionTime) {
        this.resolutionTime = resolutionTime;
    }

    @Override
    public int compareTo(PgLogEntity o) {
        int get=0;
        try {
            //get= Long.parseLong(getComplaintTime()).compareTo(Long.parseLong(o.getComplaintTime()));
            get= getPgLevel().compareToIgnoreCase( getPgLevel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return get;
    }
}
