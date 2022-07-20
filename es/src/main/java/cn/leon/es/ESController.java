package cn.leon.es;

import cn.leon.es.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ESController {

    private SearchService searchService;

    @GetMapping("search")
    public Object search(String id) {
        return searchService.findById(id);
    }

    @GetMapping("save")
    public void search() {
        searchService.save();
    }

}
