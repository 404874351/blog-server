package com.zjc.blog.utils;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjc.blog.entity.BaseEntity;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBatisUtils {

    public static final String SORT_ASC = "asc";

    public static final String SORT_DESC = "desc";

    public static final String LOGIC_DELETE_FIELD = "deleted";

    public static Pattern underlinePattern = Pattern.compile("_(\\w)");
    public static Pattern camelPattern = Pattern.compile("[A-Z]");

    /**
     * 获取逻辑删除查询条件构造器，只关注未禁用数据
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> QueryWrapper<T> createLogicDeleteQueryWrapper(Class<T> clazz) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(LOGIC_DELETE_FIELD, BaseEntity.ENTITY_ACTIVED);
        return queryWrapper;
    }

    /**
     * 获取逻辑删除更新条件构造器，只关注未禁用数据
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> UpdateWrapper<T> createLogicDeleteUpdateWrapper(Class<T> clazz) {
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(LOGIC_DELETE_FIELD, BaseEntity.ENTITY_ACTIVED);
        return updateWrapper;
    }

    /**
     * 获取默认查询条件构造器，添加恒等条件，防止报错
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> QueryWrapper<T>  createDefaultQueryWrapper(Class<T> clazz) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1", 1);
        return queryWrapper;
    }

    /**
     * 获取默认更新条件构造器，添加恒等条件，防止报错
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> UpdateWrapper<T>  createDefaultUpdateWrapper(Class<T> clazz) {
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("1", 1);
        return updateWrapper;
    }

    /**
     * 添加模糊查找参数，自动过滤空字符串
     * @param queryWrapper
     * @param map
     * @param <T>
     */
    public static <T> void addFuzzyQueryParams (QueryWrapper<T> queryWrapper, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // 空数据，则不添加
            if(value == null || "".equals(value)) {
                continue;
            }
            // 键名需驼峰转下划线
            key = camelToUnderline(key);

            queryWrapper.like(key, value);
        }
    }

    /**
     * 驼峰转下划线风格 [A-Z] => _[a-z]
     * @param str
     * @return
     */
    public static String camelToUnderline(String str) {
        Matcher matcher = camelPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        // 对于每次匹配结果，替换为下划线和对应的小写字母
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转驼峰风格 _[a-zA-Z] => [A-Z]
     * @param str
     * @return
     */
    public static String underlineToCamel(String str) {
        Matcher matcher = underlinePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        // 对于每次匹配结果，替换为对应的大写字母
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
