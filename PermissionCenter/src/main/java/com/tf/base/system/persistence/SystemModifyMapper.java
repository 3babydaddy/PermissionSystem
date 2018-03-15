package com.tf.base.system.persistence;

import org.apache.ibatis.annotations.Param;

import com.tf.base.system.domain.SystemInfo;

public interface SystemModifyMapper {

	int modifySystemInfo(@Param(value="info")SystemInfo info);
}
