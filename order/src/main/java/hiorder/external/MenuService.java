package hiorder.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "menu", url = "${api.url.menu}")
public interface MenuService {

    @GetMapping(path = "/menus/search/findByGetMenu/{id}")
    public MenuDto getMenu(@PathVariable("id") Long id);
}
