package domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionElementProps {
    @Id
    @GeneratedValue
    private UUID id;

    @JsonProperty("key")
    private String key;

    @JsonProperty("text")
    private String text;

    @JsonProperty("wrapperStyle")
    private String wrapperStyle;

    @JsonProperty("textStyle")
    private String textStyle;

    @JsonProperty("inputStyle")
    private String inputStyle;

    @JsonProperty("url")
    private String url;

    @JsonProperty("style")
    private String style;

    public SectionElementProps() { }

    public SectionElementProps(UUID id, String key, String text, String wrapperStyle, String textStyle,
                               String inputStyle, String url, String style) {
        this.id = id;
        this.key = key;
        this.text = text;
        this.wrapperStyle = wrapperStyle;
        this.textStyle = textStyle;
        this.inputStyle = inputStyle;
        this.url = url;
        this.style = style;
    }
}