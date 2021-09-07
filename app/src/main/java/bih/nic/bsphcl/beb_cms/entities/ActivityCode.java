package bih.nic.bsphcl.beb_cms.entities;

public class ActivityCode {
    private String srNo;
    private String codeGroup;
    private String code;
    private String shortTextCode;
    private String status;

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getCodeGroup() {
        return codeGroup;
    }

    public void setCodeGroup(String codeGroup) {
        this.codeGroup = codeGroup;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortTextCode() {
        return shortTextCode;
    }

    public void setShortTextCode(String shortTextCode) {
        this.shortTextCode = shortTextCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
