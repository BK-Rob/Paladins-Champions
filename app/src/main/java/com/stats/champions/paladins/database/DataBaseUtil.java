package com.stats.champions.paladins.database;

import java.util.ArrayList;
import java.util.Map;

import co.uk.rushorm.core.AnnotationCache;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;

public class DataBaseUtil {

    public static <T extends Rush> ArrayList<T> loadDataById(Class<? extends Rush> clazz, String id) {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(clazz).getTableName();
        return (ArrayList<T>) RushCore.getInstance().load(clazz, "SELECT * FROM " + table + " WHERE id = " + id + " ORDER BY name DESC");
    }

    public static <T extends Rush> ArrayList<T> loadAllData(Class<? extends Rush> clazz) {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(clazz).getTableName();
        return (ArrayList<T>) RushCore.getInstance().load(clazz, "SELECT * FROM " + table);
    }

    public static long countData(Class<? extends Rush> clazz) {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(clazz).getTableName();
        return RushCore.getInstance().count("SELECT COUNT(*) FROM " + table);
    }
}
