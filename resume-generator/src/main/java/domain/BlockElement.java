package domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockElement {
    @Id
    @GeneratedValue
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("title")
    private String title;

    @JsonProperty("type")
    private String type;

    @JsonProperty("source")
    private String source;

    @JsonProperty("columns")
    private Integer columns;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "section_element_props_id")
    private SectionElementProps props;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BlockElement> children;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "layout_id")
    private Layout layout;

    public BlockElement() {}

    public BlockElement(UUID id, String name, String title, String type, String source, Integer columns,
                        SectionElementProps props, List<BlockElement> children, Layout layout) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.type = type;
        this.source = source;
        this.columns = columns;
        this.props = props;
        this.children = children;
        this.layout = layout;
    }
}