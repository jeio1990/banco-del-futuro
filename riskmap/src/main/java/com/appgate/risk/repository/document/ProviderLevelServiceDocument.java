package com.appgate.risk.repository.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class ProviderLevelServiceDocument {

    @Id
    private String _id;
    private String provider;
    private List<Integer> level;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<Integer> getLevel() {
        return level;
    }

    public void setLevel(List<Integer> level) {
        this.level = level;
    }
}
