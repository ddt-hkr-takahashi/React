package com.example.mapper;

import static com.example.mapper.BranchesListDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import com.example.model.BranchesList;
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
public interface BranchesListMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<BranchesList>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    BasicColumn[] selectList = BasicColumn.columnList(branchCode, prefCode, storeCode, branchName);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source Table: branches_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BranchesListResult", value = {
        @Result(column="branch_code", property="branchCode", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="pref_code", property="prefCode", jdbcType=JdbcType.CHAR),
        @Result(column="store_code", property="storeCode", jdbcType=JdbcType.CHAR),
        @Result(column="branch_name", property="branchName", jdbcType=JdbcType.VARCHAR)
    })
    List<BranchesList> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source Table: branches_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BranchesListResult")
    Optional<BranchesList> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source Table: branches_list")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, branchesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source Table: branches_list")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, branchesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1359044+09:00", comments="Source Table: branches_list")
    default int deleteByPrimaryKey(Integer branchCode_) {
        return delete(c -> 
            c.where(branchCode, isEqualTo(branchCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    default int insert(BranchesList row) {
        return MyBatis3Utils.insert(this::insert, row, branchesList, c ->
            c.map(branchCode).toProperty("branchCode")
            .map(prefCode).toProperty("prefCode")
            .map(storeCode).toProperty("storeCode")
            .map(branchName).toProperty("branchName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    default int insertMultiple(Collection<BranchesList> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, branchesList, c ->
            c.map(branchCode).toProperty("branchCode")
            .map(prefCode).toProperty("prefCode")
            .map(storeCode).toProperty("storeCode")
            .map(branchName).toProperty("branchName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    default int insertSelective(BranchesList row) {
        return MyBatis3Utils.insert(this::insert, row, branchesList, c ->
            c.map(branchCode).toPropertyWhenPresent("branchCode", row::getBranchCode)
            .map(prefCode).toPropertyWhenPresent("prefCode", row::getPrefCode)
            .map(storeCode).toPropertyWhenPresent("storeCode", row::getStoreCode)
            .map(branchName).toPropertyWhenPresent("branchName", row::getBranchName)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    default Optional<BranchesList> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, branchesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    default List<BranchesList> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, branchesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    default List<BranchesList> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, branchesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    default Optional<BranchesList> selectByPrimaryKey(Integer branchCode_) {
        return selectOne(c ->
            c.where(branchCode, isEqualTo(branchCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, branchesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    static UpdateDSL<UpdateModel> updateAllColumns(BranchesList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(branchCode).equalTo(row::getBranchCode)
                .set(prefCode).equalTo(row::getPrefCode)
                .set(storeCode).equalTo(row::getStoreCode)
                .set(branchName).equalTo(row::getBranchName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(BranchesList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(branchCode).equalToWhenPresent(row::getBranchCode)
                .set(prefCode).equalToWhenPresent(row::getPrefCode)
                .set(storeCode).equalToWhenPresent(row::getStoreCode)
                .set(branchName).equalToWhenPresent(row::getBranchName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1369044+09:00", comments="Source Table: branches_list")
    default int updateByPrimaryKey(BranchesList row) {
        return update(c ->
            c.set(prefCode).equalTo(row::getPrefCode)
            .set(storeCode).equalTo(row::getStoreCode)
            .set(branchName).equalTo(row::getBranchName)
            .where(branchCode, isEqualTo(row::getBranchCode))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1379041+09:00", comments="Source Table: branches_list")
    default int updateByPrimaryKeySelective(BranchesList row) {
        return update(c ->
            c.set(prefCode).equalToWhenPresent(row::getPrefCode)
            .set(storeCode).equalToWhenPresent(row::getStoreCode)
            .set(branchName).equalToWhenPresent(row::getBranchName)
            .where(branchCode, isEqualTo(row::getBranchCode))
        );
    }
}