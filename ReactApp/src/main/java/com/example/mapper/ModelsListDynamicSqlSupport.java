package com.example.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class ModelsListDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source Table: models_list")
    public static final ModelsList modelsList = new ModelsList();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source field: models_list.model_code")
    public static final SqlColumn<String> modelCode = modelsList.modelCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source field: models_list.maker_code")
    public static final SqlColumn<String> makerCode = modelsList.makerCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source field: models_list.type_code")
    public static final SqlColumn<String> typeCode = modelsList.typeCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source field: models_list.model_name")
    public static final SqlColumn<String> modelName = modelsList.modelName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source field: models_list.list_price")
    public static final SqlColumn<Integer> listPrice = modelsList.listPrice;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1419153+09:00", comments="Source Table: models_list")
    public static final class ModelsList extends AliasableSqlTable<ModelsList> {
        public final SqlColumn<String> modelCode = column("model_code", JDBCType.CHAR);

        public final SqlColumn<String> makerCode = column("maker_code", JDBCType.CHAR);

        public final SqlColumn<String> typeCode = column("type_code", JDBCType.CHAR);

        public final SqlColumn<String> modelName = column("model_name", JDBCType.VARCHAR);

        public final SqlColumn<Integer> listPrice = column("list_price", JDBCType.INTEGER);

        public ModelsList() {
            super("models_list", ModelsList::new);
        }
    }
}