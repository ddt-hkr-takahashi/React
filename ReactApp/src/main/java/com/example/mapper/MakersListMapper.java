package com.example.mapper;

import static com.example.mapper.MakersListDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import com.example.model.MakersList;
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
public interface MakersListMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<MakersList>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    BasicColumn[] selectList = BasicColumn.columnList(makerCode, makerName);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1449167+09:00", comments="Source Table: makers_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MakersListResult", value = {
        @Result(column="maker_code", property="makerCode", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="maker_name", property="makerName", jdbcType=JdbcType.VARCHAR)
    })
    List<MakersList> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1449167+09:00", comments="Source Table: makers_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MakersListResult")
    Optional<MakersList> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, makersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, makersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default int deleteByPrimaryKey(String makerCode_) {
        return delete(c -> 
            c.where(makerCode, isEqualTo(makerCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default int insert(MakersList row) {
        return MyBatis3Utils.insert(this::insert, row, makersList, c ->
            c.map(makerCode).toProperty("makerCode")
            .map(makerName).toProperty("makerName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default int insertMultiple(Collection<MakersList> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, makersList, c ->
            c.map(makerCode).toProperty("makerCode")
            .map(makerName).toProperty("makerName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default int insertSelective(MakersList row) {
        return MyBatis3Utils.insert(this::insert, row, makersList, c ->
            c.map(makerCode).toPropertyWhenPresent("makerCode", row::getMakerCode)
            .map(makerName).toPropertyWhenPresent("makerName", row::getMakerName)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default Optional<MakersList> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, makersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default List<MakersList> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, makersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default List<MakersList> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, makersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default Optional<MakersList> selectByPrimaryKey(String makerCode_) {
        return selectOne(c ->
            c.where(makerCode, isEqualTo(makerCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, makersList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    static UpdateDSL<UpdateModel> updateAllColumns(MakersList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(makerCode).equalTo(row::getMakerCode)
                .set(makerName).equalTo(row::getMakerName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(MakersList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(makerCode).equalToWhenPresent(row::getMakerCode)
                .set(makerName).equalToWhenPresent(row::getMakerName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default int updateByPrimaryKey(MakersList row) {
        return update(c ->
            c.set(makerName).equalTo(row::getMakerName)
            .where(makerCode, isEqualTo(row::getMakerCode))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1459027+09:00", comments="Source Table: makers_list")
    default int updateByPrimaryKeySelective(MakersList row) {
        return update(c ->
            c.set(makerName).equalToWhenPresent(row::getMakerName)
            .where(makerCode, isEqualTo(row::getMakerCode))
        );
    }
}