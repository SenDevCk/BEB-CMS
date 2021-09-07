package bih.nic.bsphcl.beb_cms.servises;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.entities.ActivityCode;
import bih.nic.bsphcl.beb_cms.entities.ActivityGroup;
import bih.nic.bsphcl.beb_cms.entities.ComplainCountIDWiseEntity;
import bih.nic.bsphcl.beb_cms.entities.ComplainCountTypeWiseEntity;
import bih.nic.bsphcl.beb_cms.entities.ComplainDetailsEntity;
import bih.nic.bsphcl.beb_cms.entities.ComplainEntity;
import bih.nic.bsphcl.beb_cms.entities.PgLogEntity;
import bih.nic.bsphcl.beb_cms.entities.UserInformationEntity;

/**
 * Created by Chandan Singh on 12-06-2018.
 */
public class WebServiceHelper {

    /*
    public static Versioninfo CheckVersion(String res) {
        Versioninfo versioninfo = null;
        try {
            JSONObject jsonObject = new JSONObject(res);
            versioninfo = new Versioninfo();
            versioninfo.setAdminMsg(jsonObject.getString("adminMsg"));
            versioninfo.setAdminTitle(jsonObject.getString("adminTitle"));
            versioninfo.setUpdateMsg(jsonObject.getString("updateMsg"));
            versioninfo.setUpdateTile(jsonObject.getString("updateTitle"));
            versioninfo.setAppUrl(jsonObject.getString("appUrl"));
            versioninfo.setRole(jsonObject.getString("role"));
            versioninfo.setAppversion(jsonObject.getString("version"));
            versioninfo.setPriority(jsonObject.getString("priority"));
            versioninfo.setVerUpdated(jsonObject.getBoolean("isUpdated"));
            versioninfo.setValidDevice(jsonObject.getBoolean("isValidDev"));
        } catch (Exception e) {
            e.printStackTrace();
            //Utiilties.writeIntoLog(Log.getStackTraceString(e));
            return null;
        }
        return versioninfo;
    }
*/
    public static UserInformationEntity loginParser(String res) {
        UserInformationEntity userInfo2 = null;
        try {
            JSONObject jsonObject = new JSONObject(res);
            userInfo2 = new UserInformationEntity();
            if (jsonObject.has("msg"))
                userInfo2.setMessageString(jsonObject.getString("msg"));
            if (jsonObject.has("userId")) userInfo2.setUserID(jsonObject.getString("userId"));
            if (jsonObject.has("name")) userInfo2.setUserName(jsonObject.getString("name"));
            if (jsonObject.has("authenticated"))
                userInfo2.setAuthenticated(jsonObject.getBoolean("authenticated"));
            if (jsonObject.has("imeiNo")) userInfo2.setImeiNo(jsonObject.getString("imeiNo"));
            if (jsonObject.has("designation"))
                userInfo2.setRole(jsonObject.getString("designation"));
            if (jsonObject.has("subDivId"))
                userInfo2.setSubDivision(jsonObject.getString("subDivId"));
            if (jsonObject.has("mobileNo"))
                userInfo2.setContactNo(jsonObject.getString("mobileNo"));
            if (jsonObject.has("emailId")) userInfo2.setEmail(jsonObject.getString("emailId"));
            if (jsonObject.has("subDivId")) userInfo2.setDivision(jsonObject.getString("subDivId"));
            if (jsonObject.has("secId")) userInfo2.setDivision(jsonObject.getString("secId"));
        } catch (Exception e) {
            e.printStackTrace();
            //Utiilties.writeIntoLog(Log.getStackTraceString(e));
            return null;
        }
        return userInfo2;
    }


    public static ArrayList<ComplainCountTypeWiseEntity> complainCountParser(String json_string) {
        ArrayList<ComplainCountTypeWiseEntity> res = null;
        try {
            JSONArray jsonArray = new JSONArray(json_string);
            res = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ComplainCountTypeWiseEntity complainCountTypeWiseEntity = new ComplainCountTypeWiseEntity();
                if (jsonObject.has("Complaint_type"))
                    complainCountTypeWiseEntity.setComplaint_type(jsonObject.getString("Complaint_type"));
                if (jsonObject.has("compl_id"))
                    complainCountTypeWiseEntity.setCompl_id(jsonObject.getString("compl_id"));
                if (jsonObject.has("Total"))
                    complainCountTypeWiseEntity.setTotal(jsonObject.getString("Total"));
                res.add(complainCountTypeWiseEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static ArrayList<ComplainCountIDWiseEntity> complainCountParser2(String json_string) {
        ArrayList<ComplainCountIDWiseEntity> res = null;
        try {
            JSONArray jsonArray = new JSONArray(json_string);
            res = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ComplainCountIDWiseEntity complainCountTypeWiseEntity = new ComplainCountIDWiseEntity();
                if (jsonObject.has("Complaint_status"))
                    complainCountTypeWiseEntity.setComplaint_type(jsonObject.getString("Complaint_status"));
                if (jsonObject.has("compl_id"))
                    complainCountTypeWiseEntity.setCompl_id(jsonObject.getString("compl_id"));
                if (jsonObject.has("Total"))
                    complainCountTypeWiseEntity.setTotal(jsonObject.getString("Total"));
                res.add(complainCountTypeWiseEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static ArrayList<ComplainEntity> complainListParser(String json_string) {
        ArrayList<ComplainEntity> res = null;
        try {
            JSONArray jsonArray = new JSONArray(json_string);
            res = new ArrayList<ComplainEntity>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ComplainEntity complainCountEntity = new ComplainEntity();
                if (jsonObject.has("ComplaintNo")) complainCountEntity.setComplaintNo(jsonObject.getString("ComplaintNo"));
                if (jsonObject.has("ComplaintType"))
                    complainCountEntity.setComplaintType(jsonObject.getString("ComplaintType"));
                if (jsonObject.has("ConatactNo"))
                    complainCountEntity.setConatactNo(jsonObject.getString("ConatactNo"));
                if (jsonObject.has("LastUpdate"))
                    complainCountEntity.setLastUpdate(jsonObject.getString("LastUpdate"));
                if (jsonObject.has("ComplaintTime"))
                    complainCountEntity.setComplaintTime(jsonObject.getString("ComplaintTime"));
                if (jsonObject.has("LastPlannerGroup"))
                    complainCountEntity.setLastPlannerGroup(jsonObject.getString("LastPlannerGroup"));
                if (jsonObject.has("Name"))
                    complainCountEntity.setName(jsonObject.getString("Name"));
                if (jsonObject.has("Consumer Id"))
                    complainCountEntity.setConsumer_Id(jsonObject.getString("Consumer Id"));
                if (jsonObject.has("EscalationLevel"))
                    complainCountEntity.setEscalationLevel(jsonObject.getString("EscalationLevel"));
                res.add(complainCountEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static ComplainDetailsEntity complainParser(String json_res) {
        ComplainDetailsEntity complainDetailsEntity = null;
        try {
            complainDetailsEntity = new ComplainDetailsEntity();
            JSONObject jsonObject = new JSONObject(json_res);
            Log.d("json_st",json_res);
            if (jsonObject.has("compNo"))
                complainDetailsEntity.setCompNo(jsonObject.getString("compNo"));
            if (jsonObject.has("div")) complainDetailsEntity.setDiv(jsonObject.getString("div"));
            if (jsonObject.has("subDivId"))
                complainDetailsEntity.setSubDivId(jsonObject.getString("subDivId"));
            if (jsonObject.has("section"))
                complainDetailsEntity.setSection(jsonObject.getString("section"));

            if (jsonObject.has("divName")) complainDetailsEntity.setDivName(jsonObject.getString("divName"));
            if (jsonObject.has("subDivName"))
                complainDetailsEntity.setSubDivName(jsonObject.getString("subDivName"));
            if (jsonObject.has("sectionName"))
                complainDetailsEntity.setSectionName(jsonObject.getString("sectionName"));

            if (jsonObject.has("escalationLevel"))
                complainDetailsEntity.setAscalationLevel(jsonObject.getString("escalationLevel"));
            if (jsonObject.has("complTypeName"))
                complainDetailsEntity.setComplTypeName(jsonObject.getString("complTypeName"));
            if (jsonObject.has("complSubTypeName"))
                complainDetailsEntity.setComplSubTypeName(jsonObject.getString("complSubTypeName"));

            if (jsonObject.has("complId"))
                complainDetailsEntity.setComplId(jsonObject.getString("complId"));
            if (jsonObject.has("complSubId"))
                complainDetailsEntity.setComplSubId(jsonObject.getString("complSubId"));
            if (jsonObject.has("consName"))
                complainDetailsEntity.setConsName(jsonObject.getString("consName"));
            if (jsonObject.has("conId"))
                complainDetailsEntity.setConId(jsonObject.getString("conId"));
            if (jsonObject.has("consName"))
                complainDetailsEntity.setConsName(jsonObject.getString("consName"));
            if (jsonObject.has("plannerGroup"))
                complainDetailsEntity.setPlannerGroup(jsonObject.getString("plannerGroup"));
            if (jsonObject.has("complStatus"))
                complainDetailsEntity.setComplStatus(jsonObject.getString("complStatus"));
            if (jsonObject.has("entryTime"))
                complainDetailsEntity.setEntryTime(jsonObject.getString("entryTime"));
            if (jsonObject.has("contactNo"))
                complainDetailsEntity.setContactNo(jsonObject.getString("contactNo"));
            if (jsonObject.has("complDesc"))
                complainDetailsEntity.setComplDesc(jsonObject.getString("complDesc"));
            if (jsonObject.has("enteredBy"))
                complainDetailsEntity.setEnteredBy(jsonObject.getString("enteredBy"));
            if (jsonObject.has("responsibleUser"))
                complainDetailsEntity.setResponsibleUser(jsonObject.getString("responsibleUser"));
            if (jsonObject.has("address1"))
                complainDetailsEntity.setAddress1(jsonObject.getString("address1"));
            if (jsonObject.has("address2"))
                complainDetailsEntity.setAddress2(jsonObject.getString("address2"));
            if (jsonObject.has("lastPlanrGroup"))
                complainDetailsEntity.setLastPlanrGroup(jsonObject.getString("lastPlanrGroup"));
            if (jsonObject.has("lastUpdateTime"))
                complainDetailsEntity.setLastUpdateTime(jsonObject.getString("lastUpdateTime"));
            if (jsonObject.has("pglog")) {
                JSONArray jsonArray = new JSONArray(jsonObject.getString("pglog"));
                ArrayList<PgLogEntity> pgLogEntities = new ArrayList<>();
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        PgLogEntity pgLogEntity = new PgLogEntity();
                        if (jsonObject1.has("id")) pgLogEntity.setId(jsonObject1.getString("id"));
                        if (jsonObject1.has("pgLevel"))
                            pgLogEntity.setPgLevel(jsonObject1.getString("pgLevel"));
                        if (jsonObject1.has("status"))
                            pgLogEntity.setStatus(jsonObject1.getString("status"));
                        if (jsonObject1.has("remarks")) {
                            if (jsonObject1.isNull("remarks")) pgLogEntity.setRemarks("N/A");
                            else pgLogEntity.setRemarks(jsonObject1.getString("remarks"));
                        }else{
                            pgLogEntity.setRemarks("N/A");
                        }
                        if (jsonObject1.has("reopenCount"))
                            pgLogEntity.setReopenCount(jsonObject1.getString("reopenCount"));
                        if (jsonObject1.has("allotmentTime"))
                            pgLogEntity.setAllotmentTime(jsonObject1.getString("allotmentTime"));
                        if (jsonObject1.has("resolutionTime"))
                            pgLogEntity.setResolutionTime(jsonObject1.getString("resolutionTime"));
                        pgLogEntities.add(pgLogEntity);
                    }
                }
                complainDetailsEntity.setPgLogEntities(pgLogEntities);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return complainDetailsEntity;
    }

    public static ArrayList<ActivityGroup> activityGroupParser(String json_res) {
        ArrayList<ActivityGroup> activityGroups=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                ActivityGroup activityGroup=new ActivityGroup();
                activityGroup.setCodeGroup(jsonObject.getString("codeGroup"));
                activityGroup.setSrNo(String.valueOf(jsonObject.getInt("srNo")));
                activityGroup.setCodeGroupDesc(jsonObject.getString("codeGroupDesc"));
                activityGroup.setStatus(jsonObject.getString("status"));
                activityGroups.add(activityGroup);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return activityGroups;
    }

    public static ArrayList<ActivityCode> activityCodeParser(String json_res) {
        ArrayList<ActivityCode> activityCodes=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                ActivityCode activityCode=new ActivityCode();
                activityCode.setSrNo(String.valueOf(jsonObject.getInt("srNo")));
                activityCode.setCodeGroup(jsonObject.getString("codeGroup"));
                activityCode.setCode(jsonObject.getString("code"));
                activityCode.setShortTextCode(jsonObject.getString("shortTextCode"));
                activityCode.setStatus(jsonObject.getString("status"));
                activityCodes.add(activityCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return activityCodes;
    }
}
