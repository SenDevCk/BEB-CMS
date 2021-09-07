package bih.nic.bsphcl.beb_cms.interfaces;


import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.entities.ComplainCountIDWiseEntity;
import bih.nic.bsphcl.beb_cms.entities.ComplainCountTypeWiseEntity;

public interface ComplaintCountIDWiseBinder {

    void countFound(ArrayList<ComplainCountIDWiseEntity> grivanceEntityArrayList);
    void countNotFound(String response);
}
