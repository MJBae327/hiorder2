package hiorder.infra;

import hiorder.domain.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping("/menus")
public class MenuController {
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url.inventory}")
    private String inventoryUrl;

    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        Menu savedMenu = menuRepository.save(menu);
        return ResponseEntity.ok(savedMenu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
        return menuRepository.findById(id)
                .map(existingMenu -> {
                    BeanUtils.copyProperties(menu, existingMenu, "id");
                    Menu updatedMenu = menuRepository.save(existingMenu);
                    return ResponseEntity.ok(updatedMenu);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id) {
        return menuRepository.findById(id)
                .map(menu -> {
                    menuRepository.delete(menu);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenu(@PathVariable Long id) {
        return menuRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = (List<Menu>) menuRepository.findAll();
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/{id}/check-inventory")
    public ResponseEntity<?> checkInventory(@PathVariable Long id) {
        return menuRepository.findById(id)
                .map(menu -> {
                    String checkUrl = inventoryUrl + "/inventories/check?menuId=" + id;
                    ResponseEntity<String> response = restTemplate.getForEntity(checkUrl, String.class);
                    return ResponseEntity.ok(response.getBody());
                })
                .orElse(ResponseEntity.notFound().build());
    }
}