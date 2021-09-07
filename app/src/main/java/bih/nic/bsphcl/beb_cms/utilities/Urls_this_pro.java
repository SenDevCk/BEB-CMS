package bih.nic.bsphcl.beb_cms.utilities;

/**
 * Created by NIC2 on 03-07-2018.
 */

public class Urls_this_pro {


    //local
    private static final String BASE_URL="http://192.168.1.95:8080/CRM/";
    public static final String LOG_IN_URL = BASE_URL+"OfficeUser/authOfficer?reqStr=";
    public static final String DOW_COM_COUNT = BASE_URL+"CrmComplaint/ComplaintCountbyType?encString=";
    public static final String DOW_COM_COUNT2 = BASE_URL+"CrmComplaint/ComplaintCountbyStatus?encString=";
    public static final String DOW_COM_LIST = BASE_URL+"CrmComplaint/ComplaintList?encString=";
    //public static final String DOW_COMPLAIN = "http://192.168.1.95:8080/CRM/CrmComplaint/fetchDataForReopen?complNo=";
    public static final String DOW_COMPLAIN_DET = BASE_URL+"CrmComplaint/getComplaintbyId?complNo=";
    public static final String LOAD_ACTIVITY_GROUP = BASE_URL+"ActivityMaster/getActivity";
    public static final String LOAD_ACTIVITY_CODE = BASE_URL+"ActivityMaster/getSubActivity";
    public static final String LOAD_CLO_COM = "";
}