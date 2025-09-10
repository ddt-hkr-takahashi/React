package com.example.model;

import jakarta.annotation.Generated;

public class MakersList {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1449167+09:00", comments="Source field: makers_list.maker_code")
    private String makerCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1449167+09:00", comments="Source field: makers_list.maker_name")
    private String makerName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1449167+09:00", comments="Source field: makers_list.maker_code")
    public String getMakerCode() {
        return makerCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1449167+09:00", comments="Source field: makers_list.maker_code")
    public void setMakerCode(String makerCode) {
        this.makerCode = makerCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1449167+09:00", comments="Source field: makers_list.maker_name")
    public String getMakerName() {
        return makerName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1449167+09:00", comments="Source field: makers_list.maker_name")
    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }
}