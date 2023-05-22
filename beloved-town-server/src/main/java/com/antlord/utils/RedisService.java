/*
 * Copyright 2021-2021 Wenge Group Holding Ltd.
 */

package com.antlord.utils;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Redis基础类
 * </p>
 *
 * @author yangyunjun
 * @since 2021-08-24
 */
@EnableConfigurationProperties({RedisProperties.class})
@Component
public class RedisService {
    /**
     * 注入RedisTemplate
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 注入StringRedisTemplate
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * delete by key
     *
     * @param key
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    public void del(Collection<String> keys) {
        if (keys != null && keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    public void setToday(String key, Object value) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(now.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, 1);
        long times = cal.getTimeInMillis() - now.getTimeInMillis();
        setEx(key, value, (int) (times / 1000));
    }

    /**
     * @param pattern
     */
    public void batchDel(String... pattern) {
        for (String kp : pattern) {
            redisTemplate.delete(redisTemplate.keys(kp + "*"));
        }
    }

    /**
     * @param key
     * @return
     */
    public Integer getInt(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (!Strings.isNullOrEmpty(value)) {
            return Integer.valueOf(value);
        }
        return null;
    }

    /**
     * @param key
     * @return
     */
    public String getStr(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    /**
     * @param key
     * @param retain
     * @return
     */
    public String getStr(String key, boolean retain) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (!retain) {
            redisTemplate.delete(key);
        }
        return value;
    }

    /**
     * @param key
     * @return
     */
    public Object getObject(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * @param key
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(String key, Class<T> clazz) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    /**
     * @param key
     * @param retain
     * @return
     */
    public Object getObject(String key, boolean retain) {
        Object obj = redisTemplate.boundValueOps(key).get();
        if (!retain) {
            redisTemplate.delete(key);
        }
        return obj;
    }


    /**
     * get value from cache
     *
     * @param key
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    /**
     * 根据key获取对象
     *
     * @param key the key
     * @param valueSerializer 序列化
     * @return the string
     */
    public Object get(final String key, RedisSerializer<Object> valueSerializer) {
        byte[] rawKey = rawKey(key);
        return redisTemplate.execute(connection -> deserializeValue(connection.get(rawKey), valueSerializer), true);
    }

    /**
     * set value to cache
     *
     * @param key
     * @param value
     * @param time
     */
    public void setEx(String key, Object value, int time) {
        if (value.getClass().equals(String.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Integer.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Double.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Float.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Short.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Long.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Boolean.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
        expire(key, time);
    }

    /**
     * @param key
     * @param delta
     * @return
     */
    public double decr(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    public long decr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * @param key
     * @param delta
     * @return
     */
    public double incr(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * get double value from cache
     *
     * @param key
     * @return
     */
    public double getDouble(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (!Strings.isNullOrEmpty(value)) {
            return Double.valueOf(value);
        }
        return 0d;
    }

    /**
     * set Double to cache
     *
     * @param key
     * @param value
     * @param time
     */
    public void setDouble(String key, double value, int time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * set Integer to cache
     *
     * @param key
     * @param value
     * @param time
     */
    public void setInt(String key, int value, int time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加到带有 过期时间的  缓存
     *
     * @param key   redis主键
     * @param value 值
     * @param time  过期时间(单位秒)
     */
    public void setExpire(final byte[] key, final byte[] value, final long time) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.setEx(key, time, value);
            return 1L;
        });
    }

    /**
     * 添加到带有 过期时间的  缓存
     *
     * @param key   redis主键
     * @param value 值
     * @param time  过期时间
     * @param timeUnit  过期时间单位
     */
    public void setExpire(final String key, final Object value, final long time, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }
    public void setExpire(final String key, final Object value, final long time) {
        this.setExpire(key, value, time, TimeUnit.SECONDS);
    }
    public void setExpire(final String key, final Object value, final long time, final TimeUnit timeUnit, RedisSerializer<Object> valueSerializer) {
        byte[] rawKey = rawKey(key);
        byte[] rawValue = rawValue(value, valueSerializer);

        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                potentiallyUsePsetEx(connection);
                return null;
            }
            public void potentiallyUsePsetEx(RedisConnection connection) {
                if (!TimeUnit.MILLISECONDS.equals(timeUnit) || !failsafeInvokePsetEx(connection)) {
                    connection.setEx(rawKey, TimeoutUtils.toSeconds(time, timeUnit), rawValue);
                }
            }
            private boolean failsafeInvokePsetEx(RedisConnection connection) {
                boolean failed = false;
                try {
                    connection.pSetEx(rawKey, time, rawValue);
                } catch (UnsupportedOperationException e) {
                    failed = true;
                }
                return !failed;
            }
        }, true);
    }

    /**
     * 一次性添加数组到   过期时间的  缓存，不用多次连接，节省开销
     *
     * @param keys   redis主键数组
     * @param values 值数组
     * @param time   过期时间(单位秒)
     */
    public void setExpire(final String[] keys, final Object[] values, final long time) {
        for (int i = 0; i < keys.length; i++) {
            redisTemplate.opsForValue().set(keys[i], values[i], time, TimeUnit.SECONDS);
        }
    }

    public void setListValue(String key, long idx, Object value) {
        redisTemplate.opsForList().set(key, idx, value);
    }

    public Long getListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public void trimList(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    public Object remove(String key, long idx, Object o) {
        return redisTemplate.opsForList().remove(key, idx, o);
    }

    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }


    public List<Object> range(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public List<Object> range(String key, int start, int end, RedisSerializer<Object> valueSerializer) {
        byte[] rawKey = rawKey(key);
        return redisTemplate.execute(connection -> deserializeValues(connection.lRange(rawKey, start, end), valueSerializer), true);
    }


    public Long leftPush(String key, List<?> values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }


    public Long leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public Long rightPush(String key, List<?> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }


    public Long rightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }


    /**
     * set values to map cache
     *
     * @param key
     * @param map
     */
    public <T> void setMap(String key, Map<?, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * add values to the map cache
     *
     * @param key
     * @param map
     */
    public void addMap(String key, Map<?, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * add value the map cache
     *
     * @param key   cache key
     * @param field the key in the map
     * @param value the value of key is filed in the map
     */
    public void addMap(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);

    }

    /**
     * @param key   key cache key
     * @param field the key in the map
     * @param obj   the Object added to the map cache
     */
    public <T> void addMap(String key, String field, T obj) {
        redisTemplate.opsForHash().put(key, field, obj);
    }

    /**
     * get map cache
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> Map<String, T> mget(String key, Class<T> clazz) {
        BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);
        return boundHashOperations.entries();
    }

    /**
     * get the value in the map
     *
     * @param key
     * @param field
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapField(String key, String field, Class<T> clazz) {
        return (T) redisTemplate.boundHashOps(key).get(field);
    }

    /**
     * delete the value in the map
     *
     * @param key
     * @param field
     */
    public Long deleteMapField(String key, Object... field) {
        BoundHashOperations<String, String, ?> boundHashOperations = redisTemplate.boundHashOps(key);
        return boundHashOperations.delete(field);
    }

    /**
     * set the expire time in cache
     *
     * @param key
     * @param time
     */
    public void expire(String key, int time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    public boolean expire(String key, Long expireTime, TimeUnit timeUnit) {
        return redisTemplate.expire(key, expireTime, timeUnit);
    }

    public boolean setIfAbsent(String key, String value) {

        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public boolean setIfAbsent(String key, String value, Long expireTime, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, timeUnit);
    }


    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public void expireMin(String key, int time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.MINUTES);
        }
    }

    public void expireHour(String key, int time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.HOURS);
        }
    }

    public void expireDay(String key, int day) {
        if (day > 0) {
            redisTemplate.expire(key, day, TimeUnit.DAYS);
        }
    }

    /**
     * add value to set
     *
     * @param key
     * @param value
     */
    public void sadd(String key, String... value) {
        redisTemplate.boundSetOps(key).add(value);
    }

    /**
     * remove the value in set
     *
     * @param key
     * @param value
     */
    public void sremove(String key, String... value) {
        redisTemplate.boundSetOps(key).remove(value);
    }

    /**
     * Check if set at the bound key contains
     *
     * @param setKey
     * @param key
     * @return
     */
    public Boolean sexist(String setKey, String key) {
        return redisTemplate.boundSetOps(setKey).isMember(key);
    }


    /**
     * set key rename
     *
     * @param oldkey oldkey
     * @param newkey newkey
     */
    public void srename(String oldkey, String newkey) {
        redisTemplate.boundSetOps(oldkey).rename(newkey);
    }

    /**
     * 查找key
     *
     * @param pattern pattern
     * @return 返回
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 判断是否存在key
     *
     * @param key key
     * @return 返回
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 清空redis存储的数据
     *
     * @return the string
     */
    public String flushDB() {
        return redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }

    private byte[] rawKey(Object key) {
        Assert.notNull(key, "non null key required");

        if (key instanceof byte[]) {
            return (byte[]) key;
        }
        RedisSerializer<Object> redisSerializer = (RedisSerializer<Object>)redisTemplate.getKeySerializer();
        return redisSerializer.serialize(key);
    }
    private byte[] rawValue(Object value, RedisSerializer valueSerializer) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }

        return valueSerializer.serialize(value);
    }

    private List deserializeValues(List<byte[]> rawValues, RedisSerializer<Object> valueSerializer) {
        if (valueSerializer == null) {
            return rawValues;
        }
        return SerializationUtils.deserialize(rawValues, valueSerializer);
    }

    private Object deserializeValue(byte[] value, RedisSerializer<Object> valueSerializer) {
        if (valueSerializer == null) {
            return value;
        }
        return valueSerializer.deserialize(value);
    }
}