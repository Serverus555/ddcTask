package me.serverus.ddcTask.repository;

import me.serverus.ddcTask.model.NewsType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NewsTypeRepository extends CrudRepository<NewsType, Long> {

    @Query("select t from NewsType t where t.name = :name")
    Optional<NewsType> findByName(@Param("name") String name);
}