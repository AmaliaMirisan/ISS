package com.example.farmacy.domain;

import java.io.Serializable;

public interface Entity<ID>{
    ID getId();

    void setId(ID id);

}
