package bih.nic.bsphcl.beb_cms.entities;

public class ComplainCountTypeWiseEntity {
    private String Complaint_type;
    private String compl_id;
    private String total;



    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getComplaint_type() {
        return Complaint_type;
    }

    public void setComplaint_type(String complaint_type) {
        Complaint_type = complaint_type;
    }

    public String getCompl_id() {
        return compl_id;
    }

    public void setCompl_id(String compl_id) {
        this.compl_id = compl_id;
    }
}
