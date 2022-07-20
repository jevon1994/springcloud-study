package cn.leon.es.service;

import cn.leon.es.repo.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IndexServiceImpl implements IndexService{

    private LogRepository logRepository;


    @Override
    public void create() {

    }
}
