package top.lclong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zlatanlong
 * @Date: 2021/5/13 10:18
 */
@RestController
@RequestMapping("/api/kitten")
public class KittenController {

    @Autowired
    private KittenRepository kittenRepository;

    @GetMapping
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(kittenRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Kitten kitten) {
        return new ResponseEntity<>(kittenRepository.save(kitten), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Kitten kitten) {
        Assert.notNull(kitten.getId(), "kitten.id must not be null");
        return new ResponseEntity<>(kittenRepository.save(kitten), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Kitten kitten) {
        Assert.notNull(kitten.getId(), "kitten.id must not be null");
        kittenRepository.delete(kitten);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
