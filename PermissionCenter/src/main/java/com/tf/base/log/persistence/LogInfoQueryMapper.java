package com.tf.base.log.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.common.domain.LogInfo;

public interface LogInfoQueryMapper {

	List<LogInfo> logQuery(@Param(value="start")int start,@Param(value="rows") int rows, @Param(value="params")LogInfo params);

	int logQueryCount(@Param(value="params")LogInfo params);
}
