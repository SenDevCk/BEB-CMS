package bih.nic.bsphcl.beb_cms.interfaces;

import bih.nic.bsphcl.beb_cms.entities.ComplainDetailsEntity;

public interface ComplainDetailsListener {
    void complainDetailsFound(ComplainDetailsEntity complainDetailsEntity);

    void complainDetailsNotFound(String msg);
}
