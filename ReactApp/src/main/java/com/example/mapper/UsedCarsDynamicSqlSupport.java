package com.example.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class UsedCarsDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1111295+09:00", comments="Source Table: used_cars")
    public static final UsedCars usedCars = new UsedCars();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1111295+09:00", comments="Source field: used_cars.car_id")
    public static final SqlColumn<Integer> carId = usedCars.carId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1121302+09:00", comments="Source field: used_cars.owner_code")
    public static final SqlColumn<Integer> ownerCode = usedCars.ownerCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1121302+09:00", comments="Source field: used_cars.branch_code")
    public static final SqlColumn<Integer> branchCode = usedCars.branchCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1121302+09:00", comments="Source field: used_cars.model_code")
    public static final SqlColumn<String> modelCode = usedCars.modelCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1121302+09:00", comments="Source field: used_cars.sales_amount")
    public static final SqlColumn<Integer> salesAmount = usedCars.salesAmount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1121302+09:00", comments="Source field: used_cars.parchase_date")
    public static final SqlColumn<Date> parchaseDate = usedCars.parchaseDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2025-09-04T10:50:30.1111295+09:00", comments="Source Table: used_cars")
    public static final class UsedCars extends AliasableSqlTable<UsedCars> {
        public final SqlColumn<Integer> carId = column("car_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> ownerCode = column("owner_code", JDBCType.INTEGER);

        public final SqlColumn<Integer> branchCode = column("branch_code", JDBCType.INTEGER);

        public final SqlColumn<String> modelCode = column("model_code", JDBCType.CHAR);

        public final SqlColumn<Integer> salesAmount = column("sales_amount", JDBCType.INTEGER);

        public final SqlColumn<Date> parchaseDate = column("parchase_date", JDBCType.DATE);

        public UsedCars() {
            super("used_cars", UsedCars::new);
        }
    }
}