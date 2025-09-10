package com.example.model;

import jakarta.annotation.Generated;

public class BranchesList {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source field: branches_list.branch_code")
    private Integer branchCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source field: branches_list.pref_code")
    private String prefCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source field: branches_list.store_code")
    private String storeCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source field: branches_list.branch_name")
    private String branchName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source field: branches_list.branch_code")
    public Integer getBranchCode() {
        return branchCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source field: branches_list.branch_code")
    public void setBranchCode(Integer branchCode) {
        this.branchCode = branchCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source field: branches_list.pref_code")
    public String getPrefCode() {
        return prefCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source field: branches_list.pref_code")
    public void setPrefCode(String prefCode) {
        this.prefCode = prefCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source field: branches_list.store_code")
    public String getStoreCode() {
        return storeCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source field: branches_list.store_code")
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source field: branches_list.branch_name")
    public String getBranchName() {
        return branchName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source field: branches_list.branch_name")
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}