package webapp.resumegenerator.domain.repository;

import webapp.resumegenerator.domain.model.Template;

import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Репозиторий для работы с коллекцией "templates" в MongoDB.
 * Данный интерфейс предоставляет методы для выполнения CRUD-операция над сущностью {@link Template}.
 */
@Repository
public interface TemplateRepository extends MongoRepository<Template, UUID>, CustomTemplateRepository {
    List<Template> findByNameOrderByVersionDesc(String name);
}