package food_delivery.controller;

import food_delivery.request.MenuRequest;
import food_delivery.request.UpdateMenuRequest;
import food_delivery.response.MenuResponse;
import food_delivery.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping()
    public ResponseEntity<MenuResponse> createMenu(@RequestBody MenuRequest menuRequest) {
        return ResponseEntity.ok(menuService.createMenu(menuRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponse> getMenuWithItems(@PathVariable Long id) {
        MenuResponse menuResponse = menuService.getMenuWithItems(id);
        return ResponseEntity.ok(menuResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuById(@PathVariable Long id)
    {
        menuService.deleteMenuById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<?> updateMenu(@PathVariable Long menuId, @RequestBody UpdateMenuRequest menuRequest) {
         menuService.updateMenu(menuId, menuRequest);
        
         return ResponseEntity.noContent().build();

    }
}
