package webapp.resumeanalyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность Education для хранения данных резюме в базе данных.
 */
@Entity
@Table(name = "education")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Education {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private UUID id;

  private String description;

  private String position;

  @JsonProperty("fromYear")
  private String from_year;

  @JsonProperty("toYear")
  private String to_year;

  private String name;
}
