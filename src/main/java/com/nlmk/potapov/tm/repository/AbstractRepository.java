package com.nlmk.potapov.tm.repository;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRepository<T> {

    private static final Logger logger = LogManager.getLogger(AbstractRepository.class);
    protected final Map<String, List<T>> entityMap = new HashMap<>();
    protected List<T> entityList = new ArrayList<>();

    public int size() {
        return entityList.size();
    }

    public List<T> findAll() {
        return entityList;
    }

    public T create(final T entity) {
        entityList.add(entity);
        addToEntityMap(entity);
        return entity;
    }

    private String getEntityName(final T entity) {
        try {
            Method method = entity.getClass().getMethod("getName");
            return method.invoke(entity, null).toString();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Ошибка при вызове метода \"getName()\" у класса {}: {}", entity.getClass(), e.getMessage());
            return null;
        }
    }

    public void addToEntityMap(final T entity) {
        final String name = getEntityName(entity);
        List<T> valueList = entityMap.get(name);
        if (valueList == null || valueList.isEmpty()) {
            valueList = new ArrayList<>();
            valueList.add(entity);
            entityMap.put(name, valueList);
            return;
        }
        valueList.add(entity);
    }

    public void removeFromEntityMap(final T entity) {
        final String name = getEntityName(entity);
        List<T> valueList = entityMap.get(name);
        if (valueList == null || valueList.isEmpty()) return;
        if (valueList.size() > 1) valueList.remove(entity);
        else entityMap.remove(name);
    }

    public void saveToJson(final String filePath) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            objectMapper.writeValue(new File(filePath), entityList);
        } catch (IOException e) {
            logger.error("Ошибка записи объекта {} в файл {} : {}", entityList, filePath, e.getMessage());
        }
    }

    public void saveToXml(final String filePath) {
        final XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(new File(filePath), entityList);
        } catch (IOException e) {
            logger.error("Ошибка записи объекта {} в файл {} : {}", entityList, filePath, e.getMessage());
        }
    }

    protected void loadFromJson(final String filePath, Class clazz) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        List<T> fileList = null;
        try {
            TypeFactory t = TypeFactory.defaultInstance();
            fileList = objectMapper.readValue(new File(filePath), t.constructCollectionType(ArrayList.class, clazz));
        } catch (IOException e) {
            logger.error("Ошибка чтения из файла {} : {}", filePath, e.getMessage());
        }
        entityList.clear();
        entityMap.clear();
        for (T entity : fileList) {
            create(entity);
        }
    }

    protected void loadFromXml(final String filePath, Class clazz) {
        final XmlMapper xmlMapper = new XmlMapper();
        List<T> fileList = null;
        try {
            TypeFactory t = TypeFactory.defaultInstance();
            fileList = xmlMapper.readValue(new File(filePath), t.constructCollectionType(ArrayList.class, clazz));
        } catch (IOException e) {
            logger.error("Ошибка чтения из файла {} : {}", filePath, e.getMessage());
        }
        entityList.clear();
        entityMap.clear();
        for (T entity : fileList) {
            create(entity);
        }
    }

}
