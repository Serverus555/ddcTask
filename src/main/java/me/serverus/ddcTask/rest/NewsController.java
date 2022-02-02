package me.serverus.ddcTask.rest;

import me.serverus.ddcTask.model.News;
import me.serverus.ddcTask.model.NewsType;
import me.serverus.ddcTask.repository.NewsRepository;
import me.serverus.ddcTask.repository.NewsTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsRepository repository;
    private final NewsTypeRepository newsTypeRepository;

    @Autowired
    public NewsController(NewsRepository repository, NewsTypeRepository newsTypeRepository) {
        this.repository = repository;
        this.newsTypeRepository = newsTypeRepository;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestParam String name, @RequestParam String shortDescription,
                                             @RequestParam String description, @RequestParam long typeId) {
        NewsType newsType = newsTypeRepository.findById(typeId).orElse(null);
        if (newsType == null) {
            return ResponseEntity.badRequest().body("Required news type not exists");
        }
        News news = new News(name, shortDescription, description, newsType);
        repository.save(news);
        return ResponseEntity.ok(news.getId());
    }

    @GetMapping(path="")
    public @ResponseBody List<NewsPreview> getAll() {
        List<NewsPreview> result = new ArrayList<>();
        repository.findAll().forEach(news -> result.add(new NewsPreview(news)));
        return result;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<News> getById(@PathVariable("id") long id) {
        News news = repository.findById(id).orElse(null);
        if (news == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(news);
    }

    @GetMapping(path = "/byType/{type}")
    public @ResponseBody List<News> getByTypeId(@PathVariable("type") long typeId) {
        List<News> result = new ArrayList<>();
        return repository.findAllByTypeId(typeId);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestParam(required = false) String name,
                                         @RequestParam(required = false) String shortDescription,
                                         @RequestParam(required = false) String description,
                                         @RequestParam(required = false) Long typeId) {
        News news = repository.findById(id).orElse(null);
        if (news == null) {
            return ResponseEntity.notFound().build();
        }

        if (typeId != null) {;
            NewsType newsType = newsTypeRepository.findById(typeId).orElse(null);
            if (newsType == null) {
                return ResponseEntity.badRequest().body("Required news type not exists");
            }
            news.setType(newsType);
        }

        Optional.ofNullable(name).ifPresent(news::setName);
        Optional.ofNullable(shortDescription).ifPresent(news::setShortDescription);
        Optional.ofNullable(description).ifPresent(news::setDescription);

        repository.save(news);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        News news = repository.findById(id).orElse(null);
        if (news == null) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok(null);
    }

    public static class NewsPreview {

        public NewsPreview(News news) {
            this.id = news.getId();
            this.name = news.getName();
            this.shortDescription = news.getShortDescription();
            NewsType type= news.getType();
            typeName = type.getName();
            typeColor = type.getColor();

        }

        public long id;
        public String name;
        public String shortDescription;
        public String typeName;
        public String typeColor;
    }
}
