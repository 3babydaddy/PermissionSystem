package com.tf.base.notification.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tf.base.notification.domain.Notificationobject;

public interface NotificationobjectMapper {

	List<Map<String, String>> queryBaseList(@Param("params") Notificationobject params,@Param("start") int start,@Param("rows") int rows);

	int queryBaseListCount(@Param("params") Notificationobject params);

	List<Notificationobject> selectByPrimaryKey(@Param("params") Notificationobject params);

	int insert(@Param("params") Notificationobject notificationobject);

	int updateByPrimaryKeySelective(@Param("params") Notificationobject notificationobject);

	int delete(@Param("params") Notificationobject notificationobject);
}