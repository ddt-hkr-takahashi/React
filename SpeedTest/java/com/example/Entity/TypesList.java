package com.example.Entity;

import jakarta.annotation.Generated;

public class TypesList {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source field: types_list.type_code")
    private String typeCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source field: types_list.type_name")
    private String typeName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source field: types_list.type_code")
    public String getTypeCode() {
        return typeCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source field: types_list.type_code")
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source field: types_list.type_name")
    public String getTypeName() {
        return typeName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source field: types_list.type_name")
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}