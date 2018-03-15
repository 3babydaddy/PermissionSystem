package com.tf.base.resource.persistence;

import org.apache.ibatis.annotations.Param;

import com.tf.base.resource.domain.ResourceInfo;

public interface ResourceModifyMapper {

	int modifyResourceInfo(@Param(value="info")ResourceInfo info);

	int deleteResourceInfo(@Param(value="id")String id);
}
