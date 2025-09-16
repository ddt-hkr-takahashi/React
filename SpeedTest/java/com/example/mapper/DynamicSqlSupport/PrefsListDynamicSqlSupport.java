package com.example.mapper.DynamicSqlSupport;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class PrefsListDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.990486+09:00", comments="Source Table: prefs_list")
    public static final PrefsList prefsList = new PrefsList();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.990486+09:00", comments="Source field: prefs_list.pref_code")
    public static final SqlColumn<String> prefCode = prefsList.prefCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.990486+09:00", comments="Source field: prefs_list.pref_name")
    public static final SqlColumn<String> prefName = prefsList.prefName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.990486+09:00", comments="Source Table: prefs_list")
    public static final class PrefsList extends AliasableSqlTable<PrefsList> {
        public final SqlColumn<String> prefCode = column("pref_code", JDBCType.CHAR);

        public final SqlColumn<String> prefName = column("pref_name", JDBCType.VARCHAR);

        public PrefsList() {
            super("prefs_list", PrefsList::new);
        }
    }
}