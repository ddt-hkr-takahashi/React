package com.example.mapper.autoCreateMapper;

import static com.example.mapper.DynamicSqlSupport.UsedCarsDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import jakarta.annotation.Generated;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

import com.example.Entity.UsedCars;

@Mapper
public interface UsedCarsMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<UsedCars>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    BasicColumn[] selectList = BasicColumn.columnList(carId, ownerCode, branchCode, modelCode, salesAmount, parchaseDate);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UsedCarsResult", value = {
        @Result(column="car_id", property="carId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="owner_code", property="ownerCode", jdbcType=JdbcType.INTEGER),
        @Result(column="branch_code", property="branchCode", jdbcType=JdbcType.INTEGER),
        @Result(column="model_code", property="modelCode", jdbcType=JdbcType.CHAR),
        @Result(column="sales_amount", property="salesAmount", jdbcType=JdbcType.INTEGER),
        @Result(column="parchase_date", property="parchaseDate", jdbcType=JdbcType.DATE)
    })
    List<UsedCars> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UsedCarsResult")
    Optional<UsedCars> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, usedCars, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, usedCars, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default int deleteByPrimaryKey(Integer carId_) {
        return delete(c -> 
            c.where(carId, isEqualTo(carId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default int insert(UsedCars row) {
        return MyBatis3Utils.insert(this::insert, row, usedCars, c ->
            c.map(carId).toProperty("carId")
            .map(ownerCode).toProperty("ownerCode")
            .map(branchCode).toProperty("branchCode")
            .map(modelCode).toProperty("modelCode")
            .map(salesAmount).toProperty("salesAmount")
            .map(parchaseDate).toProperty("parchaseDate")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default int insertMultiple(Collection<UsedCars> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, usedCars, c ->
            c.map(carId).toProperty("carId")
            .map(ownerCode).toProperty("ownerCode")
            .map(branchCode).toProperty("branchCode")
            .map(modelCode).toProperty("modelCode")
            .map(salesAmount).toProperty("salesAmount")
            .map(parchaseDate).toProperty("parchaseDate")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default int insertSelective(UsedCars row) {
        return MyBatis3Utils.insert(this::insert, row, usedCars, c ->
            c.map(carId).toPropertyWhenPresent("carId", row::getCarId)
            .map(ownerCode).toPropertyWhenPresent("ownerCode", row::getOwnerCode)
            .map(branchCode).toPropertyWhenPresent("branchCode", row::getBranchCode)
            .map(modelCode).toPropertyWhenPresent("modelCode", row::getModelCode)
            .map(salesAmount).toPropertyWhenPresent("salesAmount", row::getSalesAmount)
            .map(parchaseDate).toPropertyWhenPresent("parchaseDate", row::getParchaseDate)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default Optional<UsedCars> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, usedCars, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default List<UsedCars> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, usedCars, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default List<UsedCars> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, usedCars, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default Optional<UsedCars> selectByPrimaryKey(Integer carId_) {
        return selectOne(c ->
            c.where(carId, isEqualTo(carId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, usedCars, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    static UpdateDSL<UpdateModel> updateAllColumns(UsedCars row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(carId).equalTo(row::getCarId)
                .set(ownerCode).equalTo(row::getOwnerCode)
                .set(branchCode).equalTo(row::getBranchCode)
                .set(modelCode).equalTo(row::getModelCode)
                .set(salesAmount).equalTo(row::getSalesAmount)
                .set(parchaseDate).equalTo(row::getParchaseDate);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UsedCars row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(carId).equalToWhenPresent(row::getCarId)
                .set(ownerCode).equalToWhenPresent(row::getOwnerCode)
                .set(branchCode).equalToWhenPresent(row::getBranchCode)
                .set(modelCode).equalToWhenPresent(row::getModelCode)
                .set(salesAmount).equalToWhenPresent(row::getSalesAmount)
                .set(parchaseDate).equalToWhenPresent(row::getParchaseDate);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default int updateByPrimaryKey(UsedCars row) {
        return update(c ->
            c.set(ownerCode).equalTo(row::getOwnerCode)
            .set(branchCode).equalTo(row::getBranchCode)
            .set(modelCode).equalTo(row::getModelCode)
            .set(salesAmount).equalTo(row::getSalesAmount)
            .set(parchaseDate).equalTo(row::getParchaseDate)
            .where(carId, isEqualTo(row::getCarId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9870697+09:00", comments="Source Table: used_cars")
    default int updateByPrimaryKeySelective(UsedCars row) {
        return update(c ->
            c.set(ownerCode).equalToWhenPresent(row::getOwnerCode)
            .set(branchCode).equalToWhenPresent(row::getBranchCode)
            .set(modelCode).equalToWhenPresent(row::getModelCode)
            .set(salesAmount).equalToWhenPresent(row::getSalesAmount)
            .set(parchaseDate).equalToWhenPresent(row::getParchaseDate)
            .where(carId, isEqualTo(row::getCarId))
        );
    }
}