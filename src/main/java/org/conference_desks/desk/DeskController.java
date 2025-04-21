package org.conference_desks.desk;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/desk")
@CrossOrigin(origins = "http://localhost:5173")
public class DeskController {

    private final DeskService deskService;

    public DeskController(DeskService deskService) {
        this.deskService = deskService;
    }

    @GetMapping
    public List<DeskResponse> getAllDesks() {
        return deskService.getAllDesks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeskResponse> getDeskById(@PathVariable long id) {
        DeskResponse desk = deskService.getDeskById(id);
        return ResponseEntity.ok(desk);
    }

    @PostMapping
    public ResponseEntity<DeskResponse> createDesk(@RequestBody @Valid DeskRequest deskRequest) {
        DeskResponse createdDesk = deskService.createDesk(deskRequest);
        return ResponseEntity
                .created(URI.create("/api/v1/desk/" + createdDesk.getId()))
                .body(createdDesk);
    }
//admin only
    @PutMapping("/{id}")
    public ResponseEntity<DeskResponse> updateDeskFully(@PathVariable long id, @RequestBody @Valid DeskRequest deskRequest) {
        DeskResponse updatedDeskFully = deskService.updateDeskFully(deskRequest, id);
        return ResponseEntity.ok(updatedDeskFully);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesk(@PathVariable long id){
        deskService.deleteDeskById(id);
        return ResponseEntity.noContent().build();
    }

}
