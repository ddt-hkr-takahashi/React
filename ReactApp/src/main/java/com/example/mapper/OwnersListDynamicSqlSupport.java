package com.example.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class OwnersListDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source Table: owners_list")
    public static final OwnersList ownersList = new OwnersList();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source field: owners_list.owner_code")
    public static final SqlColumn<Integer> ownerCode = ownersList.ownerCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source field: owners_list.owner_name")
    public static final SqlColumn<String> ownerName = ownersList.ownerName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source field: owners_list.sex")
    public static final SqlColumn<String> sex = ownersList.sex;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source field: owners_list.age")
    public static final SqlColumn<Integer> age = ownersList.age;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1339022+09:00", comments="Source Table: owners_list")
    public static final class OwnersList extends AliasableSqlTable<OwnersList> {
        public final SqlColumn<Integer> ownerCode = column("owner_code", JDBCType.INTEGER);

        public final SqlColumn<String> ownerName = column("owner_name", JDBCType.VARCHAR);

        public final SqlColumn<String> sex = column("sex", JDBCType.CHAR);

        public final SqlColumn<Integer> age = column("age", JDBCType.INTEGER);

        public OwnersList() {
            super("owners_list", OwnersList::new);
        }
    }
}