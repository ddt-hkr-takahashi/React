package com.example.Entity;

import jakarta.annotation.Generated;

public class OwnersList {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.owner_code")
    private Integer ownerCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.owner_name")
    private String ownerName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.sex")
    private String sex;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.age")
    private Integer age;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.owner_code")
    public Integer getOwnerCode() {
        return ownerCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.owner_code")
    public void setOwnerCode(Integer ownerCode) {
        this.ownerCode = ownerCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.owner_name")
    public String getOwnerName() {
        return ownerName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.owner_name")
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.sex")
    public String getSex() {
        return sex;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.sex")
    public void setSex(String sex) {
        this.sex = sex;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.age")
    public Integer getAge() {
        return age;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source field: owners_list.age")
    public void setAge(Integer age) {
        this.age = age;
    }
}