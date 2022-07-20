package cn.leon.storage.controller;

import cn.leon.storage.persistence.Storage;
import cn.leon.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequestMapping("/api/storage/")
@RestController
public class StorageController {

    @Autowired
    StorageService storageService;

    @GetMapping(value = "/deduct")
    public void deduct(@RequestParam String commodityCode, @RequestParam Integer count) throws SQLException {
        storageService.deduct(commodityCode, count);
    }

    @GetMapping(value = "/get/{id}")
    public Storage getById(@PathVariable("id") Integer id) {
        return storageService.get(id);
    }

    @GetMapping(value = "/batch/update")
    public void batchUpdateCond() {
        try {
            storageService.batchUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/batch/delete")
    public void batchDeleteCond() {
        try {
            storageService.batchDelete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
