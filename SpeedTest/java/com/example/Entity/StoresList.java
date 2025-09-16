package com.example.Entity;

import jakarta.annotation.Generated;

public class StoresList {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source field: stores_list.store_code")
    private String storeCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source field: stores_list.store_name")
    private String storeName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source field: stores_list.store_code")
    public String getStoreCode() {
        return storeCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source field: stores_list.store_code")
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source field: stores_list.store_name")
    public String getStoreName() {
        return storeName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source field: stores_list.store_name")
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}