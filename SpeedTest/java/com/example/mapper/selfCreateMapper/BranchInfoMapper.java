package com.example.mapper.selfCreateMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BranchInfoMapper {

	// 絞り込み検索
	List<Map<String, Object>> refineSearch(@Param("prefCode") String prefCode, @Param("storeCode") String storeCode);

	// 単一の支店情報を取得
	Map<String, Object> getBranchByCode(@Param("branchCode") String branchCode);
}
