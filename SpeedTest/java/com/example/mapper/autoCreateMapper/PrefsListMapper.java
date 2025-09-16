package com.example.mapper.autoCreateMapper;

import static com.example.mapper.DynamicSqlSupport.PrefsListDynamicSqlSupport.*;
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

import com.example.Entity.PrefsList;

@Mapper
public interface PrefsListMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<PrefsList>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    BasicColumn[] selectList = BasicColumn.columnList(prefCode, prefName);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.990486+09:00", comments="Source Table: prefs_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="PrefsListResult", value = {
        @Result(column="pref_code", property="prefCode", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="pref_name", property="prefName", jdbcType=JdbcType.VARCHAR)
    })
    List<PrefsList> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("PrefsListResult")
    Optional<PrefsList> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, prefsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, prefsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    default int deleteByPrimaryKey(String prefCode_) {
        return delete(c -> 
            c.where(prefCode, isEqualTo(prefCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    default int insert(PrefsList row) {
        return MyBatis3Utils.insert(this::insert, row, prefsList, c ->
            c.map(prefCode).toProperty("prefCode")
            .map(prefName).toProperty("prefName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    default int insertMultiple(Collection<PrefsList> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, prefsList, c ->
            c.map(prefCode).toProperty("prefCode")
            .map(prefName).toProperty("prefName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    default int insertSelective(PrefsList row) {
        return MyBatis3Utils.insert(this::insert, row, prefsList, c ->
            c.map(prefCode).toPropertyWhenPresent("prefCode", row::getPrefCode)
            .map(prefName).toPropertyWhenPresent("prefName", row::getPrefName)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    default Optional<PrefsList> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, prefsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    default List<PrefsList> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, prefsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    default List<PrefsList> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, prefsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    default Optional<PrefsList> selectByPrimaryKey(String prefCode_) {
        return selectOne(c ->
            c.where(prefCode, isEqualTo(prefCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, prefsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9914854+09:00", comments="Source Table: prefs_list")
    static UpdateDSL<UpdateModel> updateAllColumns(PrefsList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(prefCode).equalTo(row::getPrefCode)
                .set(prefName).equalTo(row::getPrefName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: prefs_list")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(PrefsList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(prefCode).equalToWhenPresent(row::getPrefCode)
                .set(prefName).equalToWhenPresent(row::getPrefName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: prefs_list")
    default int updateByPrimaryKey(PrefsList row) {
        return update(c ->
            c.set(prefName).equalTo(row::getPrefName)
            .where(prefCode, isEqualTo(row::getPrefCode))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: prefs_list")
    default int updateByPrimaryKeySelective(PrefsList row) {
        return update(c ->
            c.set(prefName).equalToWhenPresent(row::getPrefName)
            .where(prefCode, isEqualTo(row::getPrefCode))
        );
    }
}