package com.example.mapper.selfCreateMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OwnerInfoMapper {

	List<Map<String, Object>> refineSearch(
			@Param("ownerName") String ownerName,
			@Param("sex") String sex,
			@Param("minAge") String minAge,
			@Param("maxAge") String maxAge);

	int countRefinedSearch(@Param("ownerName") String ownerName, @Param("sex") String sex,
			@Param("minAge") String minAge, @Param("maxAge") String maxAge);

	Map<String, Object> getOwnerByCode(@Param("ownerCode") String ownerCode);

}
