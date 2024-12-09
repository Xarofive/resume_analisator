package webapp.resumeanalyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность PersonalData для хранения данных резюме в базе данных.
 */
@Entity
@Table(name = "personal_data")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalData {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private UUID id;

  @NotBlank
  @Size(min = 3)
  @JsonProperty("fullName")
  private String full_name;

  @JsonProperty("adress")
  private String address;

  private String bio;

  private String position;

  private Long phone;

  private String website;

  @Email
  @JsonProperty("mail")
  private String email;
}
