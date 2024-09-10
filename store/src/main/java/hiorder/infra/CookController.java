package hiorder.infra;

import hiorder.domain.*;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping("/cooks")
@Transactional
public class CookController {

    @Autowired
    CookRepository cookRepository;

    @PostMapping
    public ResponseEntity<Cook> createCook(@RequestBody Cook cook) {
        Cook savedCook = cookRepository.save(cook);
        return ResponseEntity.ok(savedCook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cook> getCook(@PathVariable Long id) {
        return cookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Cook>> getAllCooks() {
        List<Cook> cooks = (List<Cook>) cookRepository.findAll();
        return ResponseEntity.ok(cooks);
    }

    @PutMapping("/{id}/isaccept")
    public ResponseEntity<Cook> isAccept(@PathVariable Long id, @RequestBody IsAcceptCommand isAcceptCommand) {
        return cookRepository.findById(id)
                .map(cook -> {
                    cook.isAccept(isAcceptCommand);
                    Cook updatedCook = cookRepository.save(cook);
                    return ResponseEntity.ok(updatedCook);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<Cook> start(@PathVariable Long id) {
        return cookRepository.findById(id)
                .map(cook -> {
                    cook.start();
                    Cook updatedCook = cookRepository.save(cook);
                    return ResponseEntity.ok(updatedCook);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<Cook> finish(@PathVariable Long id) {
        return cookRepository.findById(id)
                .map(cook -> {
                    cook.finish();
                    Cook updatedCook = cookRepository.save(cook);
                    return ResponseEntity.ok(updatedCook);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
//>>> Clean Arch / Inbound Adaptor
