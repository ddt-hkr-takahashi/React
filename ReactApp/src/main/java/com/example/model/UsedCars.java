package com.example.model;

import jakarta.annotation.Generated;
import java.util.Date;

public class UsedCars {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1041296+09:00", comments="Source field: used_cars.car_id")
    private Integer carId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.owner_code")
    private Integer ownerCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.branch_code")
    private Integer branchCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.model_code")
    private String modelCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.sales_amount")
    private Integer salesAmount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1071302+09:00", comments="Source field: used_cars.parchase_date")
    private Date parchaseDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.car_id")
    public Integer getCarId() {
        return carId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.car_id")
    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.owner_code")
    public Integer getOwnerCode() {
        return ownerCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.owner_code")
    public void setOwnerCode(Integer ownerCode) {
        this.ownerCode = ownerCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.branch_code")
    public Integer getBranchCode() {
        return branchCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.branch_code")
    public void setBranchCode(Integer branchCode) {
        this.branchCode = branchCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.model_code")
    public String getModelCode() {
        return modelCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.model_code")
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1061307+09:00", comments="Source field: used_cars.sales_amount")
    public Integer getSalesAmount() {
        return salesAmount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1071302+09:00", comments="Source field: used_cars.sales_amount")
    public void setSalesAmount(Integer salesAmount) {
        this.salesAmount = salesAmount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1071302+09:00", comments="Source field: used_cars.parchase_date")
    public Date getParchaseDate() {
        return parchaseDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1071302+09:00", comments="Source field: used_cars.parchase_date")
    public void setParchaseDate(Date parchaseDate) {
        this.parchaseDate = parchaseDate;
    }
}