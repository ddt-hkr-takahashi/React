package com.example.mapper.autoCreateMapper;

import static com.example.mapper.DynamicSqlSupport.TypesListDynamicSqlSupport.*;
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

import com.example.Entity.TypesList;

@Mapper
public interface TypesListMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<TypesList>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    BasicColumn[] selectList = BasicColumn.columnList(typeCode, typeName);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="TypesListResult", value = {
        @Result(column="type_code", property="typeCode", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="type_name", property="typeName", jdbcType=JdbcType.VARCHAR)
    })
    List<TypesList> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("TypesListResult")
    Optional<TypesList> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, typesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, typesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default int deleteByPrimaryKey(String typeCode_) {
        return delete(c -> 
            c.where(typeCode, isEqualTo(typeCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default int insert(TypesList row) {
        return MyBatis3Utils.insert(this::insert, row, typesList, c ->
            c.map(typeCode).toProperty("typeCode")
            .map(typeName).toProperty("typeName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default int insertMultiple(Collection<TypesList> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, typesList, c ->
            c.map(typeCode).toProperty("typeCode")
            .map(typeName).toProperty("typeName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default int insertSelective(TypesList row) {
        return MyBatis3Utils.insert(this::insert, row, typesList, c ->
            c.map(typeCode).toPropertyWhenPresent("typeCode", row::getTypeCode)
            .map(typeName).toPropertyWhenPresent("typeName", row::getTypeName)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default Optional<TypesList> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, typesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default List<TypesList> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, typesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default List<TypesList> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, typesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default Optional<TypesList> selectByPrimaryKey(String typeCode_) {
        return selectOne(c ->
            c.where(typeCode, isEqualTo(typeCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, typesList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    static UpdateDSL<UpdateModel> updateAllColumns(TypesList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(typeCode).equalTo(row::getTypeCode)
                .set(typeName).equalTo(row::getTypeName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(TypesList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(typeCode).equalToWhenPresent(row::getTypeCode)
                .set(typeName).equalToWhenPresent(row::getTypeName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    default int updateByPrimaryKey(TypesList row) {
        return update(c ->
            c.set(typeName).equalTo(row::getTypeName)
            .where(typeCode, isEqualTo(row::getTypeCode))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9966559+09:00", comments="Source Table: types_list")
    default int updateByPrimaryKeySelective(TypesList row) {
        return update(c ->
            c.set(typeName).equalToWhenPresent(row::getTypeName)
            .where(typeCode, isEqualTo(row::getTypeCode))
        );
    }
}