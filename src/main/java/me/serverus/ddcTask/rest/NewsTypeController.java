package me.serverus.ddcTask.rest;

import me.serverus.ddcTask.model.NewsType;
import me.serverus.ddcTask.repository.NewsTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("newsType")
public class NewsTypeController {

    private final NewsTypeRepository repository;

    @Autowired
    public NewsTypeController(NewsTypeRepository repository) {
        this.repository = repository;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestParam String name, @RequestParam String color) {
        NewsType withSameName = repository.findByName(name).orElse(null);
        if (withSameName != null) {
            return new ResponseEntity<>(withSameName, HttpStatus.CONFLICT);
        }
        NewsType newsType = new NewsType(name, color);
        repository.save(newsType);
        return ResponseEntity.ok(newsType.getId());
    }

    @GetMapping("")
    public @ResponseBody List<NewsType> getAll() {
        List<NewsType> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsType> getById(@PathVariable long id) {
        NewsType newsType = repository.findById(id).orElse(null);
        if (newsType == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newsType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestParam(required = false) String name,
                                         @RequestParam(required = false) String color) {
        NewsType newsType = repository.findById(id).orElse(null);
        if (newsType == null) {
            return ResponseEntity.notFound().build();
        }

        Optional.ofNullable(name).ifPresent(newsType::setName);
        Optional.ofNullable(color).ifPresent(newsType::setColor);

        repository.save(newsType);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        NewsType newsType = repository.findById(id).orElse(null);
        if (newsType == null) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
