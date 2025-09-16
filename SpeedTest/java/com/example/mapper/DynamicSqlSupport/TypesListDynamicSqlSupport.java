package com.example.mapper.DynamicSqlSupport;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class TypesListDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    public static final TypesList typesList = new TypesList();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source field: types_list.type_code")
    public static final SqlColumn<String> typeCode = typesList.typeCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source field: types_list.type_name")
    public static final SqlColumn<String> typeName = typesList.typeName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9956555+09:00", comments="Source Table: types_list")
    public static final class TypesList extends AliasableSqlTable<TypesList> {
        public final SqlColumn<String> typeCode = column("type_code", JDBCType.CHAR);

        public final SqlColumn<String> typeName = column("type_name", JDBCType.VARCHAR);

        public TypesList() {
            super("types_list", TypesList::new);
        }
    }
}