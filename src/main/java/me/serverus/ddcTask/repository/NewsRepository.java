package me.serverus.ddcTask.repository;

import me.serverus.ddcTask.model.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends CrudRepository<News, Long> {

    @Query("select n from News n where n.type.id = :typeId")
    List<News> findAllByTypeId(@Param("typeId") long typeId);
}
