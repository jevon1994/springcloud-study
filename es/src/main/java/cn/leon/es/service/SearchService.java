package cn.leon.es.service;

import cn.leon.es.LogEntity;

import javax.validation.constraints.NotBlank;

public interface SearchService {

    LogEntity findById(@NotBlank String id);

    void save();

    void saveAll();

    void deleteById(String id);

    void deleteAll();

    void search(String key,String val);

}
