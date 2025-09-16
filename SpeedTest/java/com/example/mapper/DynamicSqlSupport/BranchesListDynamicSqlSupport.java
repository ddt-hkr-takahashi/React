package com.example.mapper.DynamicSqlSupport;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class BranchesListDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9887045+09:00", comments="Source Table: branches_list")
    public static final BranchesList branchesList = new BranchesList();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9887045+09:00", comments="Source field: branches_list.branch_code")
    public static final SqlColumn<Integer> branchCode = branchesList.branchCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9887045+09:00", comments="Source field: branches_list.pref_code")
    public static final SqlColumn<String> prefCode = branchesList.prefCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9887045+09:00", comments="Source field: branches_list.store_code")
    public static final SqlColumn<String> storeCode = branchesList.storeCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9887045+09:00", comments="Source field: branches_list.branch_name")
    public static final SqlColumn<String> branchName = branchesList.branchName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-02T10:18:59.9887045+09:00", comments="Source Table: branches_list")
    public static final class BranchesList extends AliasableSqlTable<BranchesList> {
        public final SqlColumn<Integer> branchCode = column("branch_code", JDBCType.INTEGER);

        public final SqlColumn<String> prefCode = column("pref_code", JDBCType.CHAR);

        public final SqlColumn<String> storeCode = column("store_code", JDBCType.CHAR);

        public final SqlColumn<String> branchName = column("branch_name", JDBCType.VARCHAR);

        public BranchesList() {
            super("branches_list", BranchesList::new);
        }
    }
}