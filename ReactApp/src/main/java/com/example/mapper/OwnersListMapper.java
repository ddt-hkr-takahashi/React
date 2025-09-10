package com.example.mapper;

import static com.example.mapper.OwnersListDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import com.example.model.OwnersList;
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

@Mapper
public interface OwnersListMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<OwnersList>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source Table: owners_list")
    BasicColumn[] selectList = BasicColumn.columnList(ownerCode, ownerName, sex, age);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source Table: owners_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="OwnersListResult", value = {
        @Result(column="owner_code", property="ownerCode", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="owner_name", property="ownerName", jdbcType=JdbcType.VARCHAR),
        @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
        @Result(column="age", property="age", jdbcType=JdbcType.INTEGER)
    })
    List<OwnersList> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source Table: owners_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("OwnersListResult")
    Optional<OwnersList> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source Table: owners_list")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, ownersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source Table: owners_list")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, ownersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source Table: owners_list")
    default int deleteByPrimaryKey(Integer ownerCode_) {
        return delete(c -> 
            c.where(ownerCode, isEqualTo(ownerCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source Table: owners_list")
    default int insert(OwnersList row) {
        return MyBatis3Utils.insert(this::insert, row, ownersList, c ->
            c.map(ownerCode).toProperty("ownerCode")
            .map(ownerName).toProperty("ownerName")
            .map(sex).toProperty("sex")
            .map(age).toProperty("age")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source Table: owners_list")
    default int insertMultiple(Collection<OwnersList> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, ownersList, c ->
            c.map(ownerCode).toProperty("ownerCode")
            .map(ownerName).toProperty("ownerName")
            .map(sex).toProperty("sex")
            .map(age).toProperty("age")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source Table: owners_list")
    default int insertSelective(OwnersList row) {
        return MyBatis3Utils.insert(this::insert, row, ownersList, c ->
            c.map(ownerCode).toPropertyWhenPresent("ownerCode", row::getOwnerCode)
            .map(ownerName).toPropertyWhenPresent("ownerName", row::getOwnerName)
            .map(sex).toPropertyWhenPresent("sex", row::getSex)
            .map(age).toPropertyWhenPresent("age", row::getAge)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source Table: owners_list")
    default Optional<OwnersList> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, ownersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source Table: owners_list")
    default List<OwnersList> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, ownersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source Table: owners_list")
    default List<OwnersList> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, ownersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source Table: owners_list")
    default Optional<OwnersList> selectByPrimaryKey(Integer ownerCode_) {
        return selectOne(c ->
            c.where(ownerCode, isEqualTo(ownerCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source Table: owners_list")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, ownersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source Table: owners_list")
    static UpdateDSL<UpdateModel> updateAllColumns(OwnersList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(ownerCode).equalTo(row::getOwnerCode)
                .set(ownerName).equalTo(row::getOwnerName)
                .set(sex).equalTo(row::getSex)
                .set(age).equalTo(row::getAge);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source Table: owners_list")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(OwnersList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(ownerCode).equalToWhenPresent(row::getOwnerCode)
                .set(ownerName).equalToWhenPresent(row::getOwnerName)
                .set(sex).equalToWhenPresent(row::getSex)
                .set(age).equalToWhenPresent(row::getAge);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source Table: owners_list")
    default int updateByPrimaryKey(OwnersList row) {
        return update(c ->
            c.set(ownerName).equalTo(row::getOwnerName)
            .set(sex).equalTo(row::getSex)
            .set(age).equalTo(row::getAge)
            .where(ownerCode, isEqualTo(row::getOwnerCode))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1349038+09:00", comments="Source Table: owners_list")
    default int updateByPrimaryKeySelective(OwnersList row) {
        return update(c ->
            c.set(ownerName).equalToWhenPresent(row::getOwnerName)
            .set(sex).equalToWhenPresent(row::getSex)
            .set(age).equalToWhenPresent(row::getAge)
            .where(ownerCode, isEqualTo(row::getOwnerCode))
        );
    }
}