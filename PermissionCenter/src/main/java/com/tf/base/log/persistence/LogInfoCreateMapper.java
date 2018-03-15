package com.tf.base.log.persistence;

import com.tf.base.common.domain.LogInfo;

public interface LogInfoCreateMapper {

	int saveLog(LogInfo info);
}
