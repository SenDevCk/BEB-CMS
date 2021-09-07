package bih.nic.bsphcl.beb_cms.interfaces;


import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.entities.ComplainCountTypeWiseEntity;

public interface ComplaintCountTypeWiseBinder {

    void countFound(ArrayList<ComplainCountTypeWiseEntity> grivanceEntityArrayList);
    void countNotFound(String response);
}
