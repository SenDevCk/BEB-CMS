package bih.nic.bsphcl.beb_cms.interfaces;


import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.entities.ComplainEntity;
import bih.nic.bsphcl.beb_cms.entities.UserInformationEntity;

public interface GrivanceListBinder {

    void grivanceFound(ArrayList<ComplainEntity> grivanceEntityArrayList);
    void grivanceNotFound(String response);
}
