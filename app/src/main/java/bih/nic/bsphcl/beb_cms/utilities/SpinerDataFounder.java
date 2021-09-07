package bih.nic.bsphcl.beb_cms.utilities;

import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.entities.ActivityCode;
import bih.nic.bsphcl.beb_cms.entities.ActivityGroup;
import bih.nic.bsphcl.beb_cms.entities.SpinnerData;

public class SpinerDataFounder {
    public static ArrayList<SpinnerData> getSpinerDataFromActivityGroup(ArrayList<ActivityGroup> objectArrayList) {
        ArrayList<SpinnerData> arrayList = new ArrayList<>();
        SpinnerData spinnerData1 = new SpinnerData();
        spinnerData1.setId("0");
        spinnerData1.setValue("--Select Activity Group--");
        arrayList.add(spinnerData1);
        for (int i = 0; i < objectArrayList.size(); i++) {
            ActivityGroup activityGroup = objectArrayList.get(i);
            SpinnerData spinnerData = new SpinnerData();
            spinnerData.setId(activityGroup.getCodeGroup());
            spinnerData.setValue(activityGroup.getCodeGroupDesc());
            arrayList.add(spinnerData);
        }

        return arrayList;
    }

    public static ArrayList<SpinnerData> getSpinerDataFromActivityCode(ArrayList<ActivityCode> objectArrayList) {

        ArrayList<SpinnerData> arrayList = new ArrayList<>();
        SpinnerData spinnerData1 = new SpinnerData();
        spinnerData1.setId("0");
        spinnerData1.setValue("--Select Activity Code--");
        arrayList.add(spinnerData1);
        for (int i = 0; i < objectArrayList.size(); i++) {
            ActivityCode activityCode = objectArrayList.get(i);
            SpinnerData spinnerData = new SpinnerData();
            spinnerData.setId(activityCode.getCode());
            spinnerData.setValue(activityCode.getShortTextCode());
            arrayList.add(spinnerData);
        }

        return arrayList;
    }
}
