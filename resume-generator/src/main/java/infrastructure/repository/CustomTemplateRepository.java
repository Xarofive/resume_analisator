package infrastructure.repository;

import domain.model.Template;
import java.time.LocalDate;
import java.util.List;

public interface CustomTemplateRepository {
  List<Template> findByName(String name);

  List<Template> findByDate(LocalDate startDate, LocalDate endDate);
}