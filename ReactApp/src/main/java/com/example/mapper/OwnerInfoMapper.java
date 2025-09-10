package com.example.mapper;

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
			@Param("maxAge") String maxAge, @Param("offset") int offset,
			@Param("limit") int limit);

	int getTotalCount(@Param("ownerName") String ownerName, @Param("sex") String sex,
			@Param("minAge") String minAge, @Param("maxAge") String maxAge);

}
