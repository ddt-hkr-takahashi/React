package com.example.mapper;

import static com.example.mapper.ModelsListDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import com.example.model.ModelsList;
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
public interface ModelsListMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<ModelsList>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1429073+09:00", comments="Source Table: models_list")
    BasicColumn[] selectList = BasicColumn.columnList(modelCode, makerCode, typeCode, modelName, listPrice);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source Table: models_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ModelsListResult", value = {
        @Result(column="model_code", property="modelCode", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="maker_code", property="makerCode", jdbcType=JdbcType.CHAR),
        @Result(column="type_code", property="typeCode", jdbcType=JdbcType.CHAR),
        @Result(column="model_name", property="modelName", jdbcType=JdbcType.VARCHAR),
        @Result(column="list_price", property="listPrice", jdbcType=JdbcType.INTEGER)
    })
    List<ModelsList> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source Table: models_list")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ModelsListResult")
    Optional<ModelsList> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source Table: models_list")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, modelsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source Table: models_list")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, modelsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source Table: models_list")
    default int deleteByPrimaryKey(String modelCode_) {
        return delete(c -> 
            c.where(modelCode, isEqualTo(modelCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source Table: models_list")
    default int insert(ModelsList row) {
        return MyBatis3Utils.insert(this::insert, row, modelsList, c ->
            c.map(modelCode).toProperty("modelCode")
            .map(makerCode).toProperty("makerCode")
            .map(typeCode).toProperty("typeCode")
            .map(modelName).toProperty("modelName")
            .map(listPrice).toProperty("listPrice")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source Table: models_list")
    default int insertMultiple(Collection<ModelsList> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, modelsList, c ->
            c.map(modelCode).toProperty("modelCode")
            .map(makerCode).toProperty("makerCode")
            .map(typeCode).toProperty("typeCode")
            .map(modelName).toProperty("modelName")
            .map(listPrice).toProperty("listPrice")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1429073+09:00", comments="Source Table: models_list")
    default int insertSelective(ModelsList row) {
        return MyBatis3Utils.insert(this::insert, row, modelsList, c ->
            c.map(modelCode).toPropertyWhenPresent("modelCode", row::getModelCode)
            .map(makerCode).toPropertyWhenPresent("makerCode", row::getMakerCode)
            .map(typeCode).toPropertyWhenPresent("typeCode", row::getTypeCode)
            .map(modelName).toPropertyWhenPresent("modelName", row::getModelName)
            .map(listPrice).toPropertyWhenPresent("listPrice", row::getListPrice)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1429073+09:00", comments="Source Table: models_list")
    default Optional<ModelsList> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, modelsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1429073+09:00", comments="Source Table: models_list")
    default List<ModelsList> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, modelsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1429073+09:00", comments="Source Table: models_list")
    default List<ModelsList> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, modelsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1429073+09:00", comments="Source Table: models_list")
    default Optional<ModelsList> selectByPrimaryKey(String modelCode_) {
        return selectOne(c ->
            c.where(modelCode, isEqualTo(modelCode_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1429073+09:00", comments="Source Table: models_list")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, modelsList, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1429073+09:00", comments="Source Table: models_list")
    static UpdateDSL<UpdateModel> updateAllColumns(ModelsList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(modelCode).equalTo(row::getModelCode)
                .set(makerCode).equalTo(row::getMakerCode)
                .set(typeCode).equalTo(row::getTypeCode)
                .set(modelName).equalTo(row::getModelName)
                .set(listPrice).equalTo(row::getListPrice);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1429073+09:00", comments="Source Table: models_list")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(ModelsList row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(modelCode).equalToWhenPresent(row::getModelCode)
                .set(makerCode).equalToWhenPresent(row::getMakerCode)
                .set(typeCode).equalToWhenPresent(row::getTypeCode)
                .set(modelName).equalToWhenPresent(row::getModelName)
                .set(listPrice).equalToWhenPresent(row::getListPrice);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1429073+09:00", comments="Source Table: models_list")
    default int updateByPrimaryKey(ModelsList row) {
        return update(c ->
            c.set(makerCode).equalTo(row::getMakerCode)
            .set(typeCode).equalTo(row::getTypeCode)
            .set(modelName).equalTo(row::getModelName)
            .set(listPrice).equalTo(row::getListPrice)
            .where(modelCode, isEqualTo(row::getModelCode))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1429073+09:00", comments="Source Table: models_list")
    default int updateByPrimaryKeySelective(ModelsList row) {
        return update(c ->
            c.set(makerCode).equalToWhenPresent(row::getMakerCode)
            .set(typeCode).equalToWhenPresent(row::getTypeCode)
            .set(modelName).equalToWhenPresent(row::getModelName)
            .set(listPrice).equalToWhenPresent(row::getListPrice)
            .where(modelCode, isEqualTo(row::getModelCode))
        );
    }
}