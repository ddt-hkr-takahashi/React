package com.example.Entity;

import jakarta.annotation.Generated;

public class PrefsList {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.990486+09:00", comments="Source field: prefs_list.pref_code")
    private String prefCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.990486+09:00", comments="Source field: prefs_list.pref_name")
    private String prefName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.990486+09:00", comments="Source field: prefs_list.pref_code")
    public String getPrefCode() {
        return prefCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.990486+09:00", comments="Source field: prefs_list.pref_code")
    public void setPrefCode(String prefCode) {
        this.prefCode = prefCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.990486+09:00", comments="Source field: prefs_list.pref_name")
    public String getPrefName() {
        return prefName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.990486+09:00", comments="Source field: prefs_list.pref_name")
    public void setPrefName(String prefName) {
        this.prefName = prefName;
    }
}