package com.example.mapper.DynamicSqlSupport;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class MakersListDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9966559+09:00", comments="Source Table: makers_list")
    public static final MakersList makersList = new MakersList();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9966559+09:00", comments="Source field: makers_list.maker_code")
    public static final SqlColumn<String> makerCode = makersList.makerCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9966559+09:00", comments="Source field: makers_list.maker_name")
    public static final SqlColumn<String> makerName = makersList.makerName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9966559+09:00", comments="Source Table: makers_list")
    public static final class MakersList extends AliasableSqlTable<MakersList> {
        public final SqlColumn<String> makerCode = column("maker_code", JDBCType.CHAR);

        public final SqlColumn<String> makerName = column("maker_name", JDBCType.VARCHAR);

        public MakersList() {
            super("makers_list", MakersList::new);
        }
    }
}