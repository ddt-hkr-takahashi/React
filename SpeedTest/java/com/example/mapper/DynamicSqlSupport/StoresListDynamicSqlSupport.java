package com.example.mapper.DynamicSqlSupport;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class StoresListDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    public static final StoresList storesList = new StoresList();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source field: stores_list.store_code")
    public static final SqlColumn<String> storeCode = storesList.storeCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source field: stores_list.store_name")
    public static final SqlColumn<String> storeName = storesList.storeName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9925062+09:00", comments="Source Table: stores_list")
    public static final class StoresList extends AliasableSqlTable<StoresList> {
        public final SqlColumn<String> storeCode = column("store_code", JDBCType.CHAR);

        public final SqlColumn<String> storeName = column("store_name", JDBCType.VARCHAR);

        public StoresList() {
            super("stores_list", StoresList::new);
        }
    }
}