package com.tf.base.common.redis.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.tf.base.common.utils.JSONUtil;



@Service
public class RedisDao extends AbstractBaseRedisDao<String, Object> {
	public void test(){
		System.out.println("test");
	}

	/**
	 * 增加
	 * @param bean
	 * @return
	 */
	public boolean add(final String keyId,final Object bean){
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(keyId);  
                byte[] name = serializer.serialize(JSONUtil.toJson(bean)); 
                return connection.setNX(key, name);  
            }  
        });  
        return result;  
	}
	
	/**
	 * 增加
	 * @param bean
	 * @return
	 */
	public boolean del(final String keyId){
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(keyId); 
                return connection.del(key) > 0;  
            }  
        });  
        return result;  
	}
	
	/**
	 * 增加
	 * 多个key  一个 value
	 * @param bean
	 * @return
	 */
	public boolean add(final String[] keyIds,final Object bean){
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                for (int i=0;i<keyIds.length;i++) {  
                    byte[] key  = serializer.serialize(keyIds[i]);  
                    byte[] name = serializer.serialize(JSONUtil.toJson(bean));  
                    connection.setNX(key, name);  
                }  
                return true;  
            }  
        }, false, true);  
        return result; 
	}
	
	/**
	 * 增加
	 * 多个key  一个 value
	 * @param bean
	 * @return
	 */
	public boolean set(final String keyId,final Object bean){
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                //for (int i=0;i<keyIds.length;i++) {  
                    byte[] key  = serializer.serialize(keyId);  
                    byte[] name = serializer.serialize(JSONUtil.toJson(bean));  
                    connection.set(key, name);  
                //}  
                return true;  
            }  
        }, false, true);  
        return result; 
	}
	/**
	 * 增加 并同时设置过期时间 秒为单位
	 * 多个key  一个 value
	 * @param bean
	 * @return
	 */
	public boolean add_ex(final String[] keyIds,final Object bean,  final long seconds){
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                for (int i=0;i<keyIds.length;i++) {  
                    byte[] key  = serializer.serialize(keyIds[i]);  
                    byte[] name = serializer.serialize(JSONUtil.toJson(bean));  
                    connection.setEx(key, seconds, name);
                }  
                return true;  
            }  
        }, false, true);  
        return result; 
	}
	/**
	 * 批量增加
	 * @param keys
	 * @param list
	 * @return
	 */
	public boolean addBatch(final List<String> keyIds,final List<Object> list){
		 Assert.notEmpty(list);  
	        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
	            public Boolean doInRedis(RedisConnection connection)  
	                    throws DataAccessException {  
	                RedisSerializer<String> serializer = getRedisSerializer();  
	                for (int i=0;i<list.size();i++) {  
	                    byte[] key  = serializer.serialize(keyIds.get(i));  
	                    byte[] name = serializer.serialize(JSONUtil.toJson(list.get(i)));  
	                    connection.setNX(key, name);  
	                }  
	                return true;  
	            }  
	        }, false, true);  
	        return result;  
	}
	
	 /**  
     * 通过key获取 
     * <br>------------------------------<br> 
     * @param keyId 
     * @return 
     */  
    public String get(final String keyId) {  
    	String result = redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key = serializer.serialize(keyId);  
                byte[] value = connection.get(key);  
                if (value == null) {  
                    return null;  
                }  
                String name = serializer.deserialize(value);  
                return name;  
            }  
        });  
        return result;  
    }
    
    /**
     * 模糊匹配keys 返回匹配的keys set
     * @param pattern
     * @return
     */
    public Set<String> getKeys(final String pattern){
    	Set<String> set=new TreeSet<>();
    	Set<byte[]> result = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {  
            public Set<byte[]> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key = serializer.serialize(pattern);  
                Set<byte[]> value = connection.keys(key);
                if (value == null) {  
                    return null;  
                }  
                return value;  
            }  
        });  
    	 RedisSerializer<String> serializer = getRedisSerializer();  
    	Iterator<byte[]> it =result.iterator();
    	while(it.hasNext()){
    		set.add(serializer.deserialize(it.next()));
    	}
        return set;  
    }
}
