package com.example.mapper.autoCreateMapper;

import static com.example.mapper.DynamicSqlSupport.StoresListDynamicSqlSupport.*;
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

import com.example.Entity.StoresList;

@Mapper
public interface StoresListMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<StoresList>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    BasicColumn[] selectList = BasicColumn.columnList(storeCode, storeName);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="StoresListResult", value = {
        @Result(column="store_code", property="storeCode", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="store_name", property="storeName", jdbcType=JdbcType.VARCHAR)
    })
    List<StoresList> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("StoresListResult")
    Optional<StoresList> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, storesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, storesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default int deleteByPrimaryKey(String storeCode_) {
        return delete(c -> 
            c.where(storeCode, isEqualTo(storeCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default int insert(StoresList row) {
        return MyBatis3Utils.insert(this::insert, row, storesList, c ->
            c.map(storeCode).toProperty("storeCode")
            .map(storeName).toProperty("storeName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default int insertMultiple(Collection<StoresList> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, storesList, c ->
            c.map(storeCode).toProperty("storeCode")
            .map(storeName).toProperty("storeName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default int insertSelective(StoresList row) {
        return MyBatis3Utils.insert(this::insert, row, storesList, c ->
            c.map(storeCode).toPropertyWhenPresent("storeCode", row::getStoreCode)
            .map(storeName).toPropertyWhenPresent("storeName", row::getStoreName)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default Optional<StoresList> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, storesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default List<StoresList> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, storesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default List<StoresList> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, storesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default Optional<StoresList> selectByPrimaryKey(String storeCode_) {
        return selectOne(c ->
            c.where(storeCode, isEqualTo(storeCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, storesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    static UpdateDSL<UpdateModel> updateAllColumns(StoresList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(storeCode).equalTo(row::getStoreCode)
                .set(storeName).equalTo(row::getStoreName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(StoresList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(storeCode).equalToWhenPresent(row::getStoreCode)
                .set(storeName).equalToWhenPresent(row::getStoreName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default int updateByPrimaryKey(StoresList row) {
        return update(c ->
            c.set(storeName).equalTo(row::getStoreName)
            .where(storeCode, isEqualTo(row::getStoreCode))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    default int updateByPrimaryKeySelective(StoresList row) {
        return update(c ->
            c.set(storeName).equalToWhenPresent(row::getStoreName)
            .where(storeCode, isEqualTo(row::getStoreCode))
        );
    }
}