package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BranchInfoMapper {

	// 絞り込み検索
	List<Map<String, Object>> refineSearch(@Param("prefCode") String prefCode, @Param("storeCode") String storeCode,
			@Param("offset") int offset,
			@Param("limit") int limit);
	
	int getTotalCount(@Param("prefCode") String prefCode, @Param("storeCode") String storeCode);
}