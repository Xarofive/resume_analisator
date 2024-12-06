package ru.kata.project.resumegenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import domain.SectionElementProps;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class SectionElementPropsTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSectionElementPropsSerializationAndDeserialization() throws JsonProcessingException {
        SectionElementProps props = createSampleSectionElementProps();
        String jsonString = objectMapper.writeValueAsString(props);
        SectionElementProps deserializedProps = objectMapper.readValue(jsonString, SectionElementProps.class);
        assertEquals(props.getKey(), deserializedProps.getKey());
        assertEquals(props.getText(), deserializedProps.getText());
        assertEquals(props.getWrapperStyle(), deserializedProps.getWrapperStyle());
        assertEquals(props.getTextStyle(), deserializedProps.getTextStyle());
        assertEquals(props.getInputStyle(), deserializedProps.getInputStyle());
        assertEquals(props.getUrl(), deserializedProps.getUrl());
        assertEquals(props.getStyle(), deserializedProps.getStyle());
    }

    @Test
    void testSectionElementPropsSerializationAndDeserialization_NullValues() throws JsonProcessingException {
        SectionElementProps props = new SectionElementProps(); // Используем конструктор по умолчанию
        String jsonString = objectMapper.writeValueAsString(props);
        SectionElementProps deserializedProps = objectMapper.readValue(jsonString, SectionElementProps.class);
        assertNull(deserializedProps.getId());
        assertNull(deserializedProps.getKey());
        assertNull(deserializedProps.getText());
        assertNull(deserializedProps.getWrapperStyle());
        assertNull(deserializedProps.getTextStyle());
        assertNull(deserializedProps.getInputStyle());
        assertNull(deserializedProps.getUrl());
        assertNull(deserializedProps.getStyle());
    }

    @Test
    void testSectionElementPropsSerializationAndDeserialization_EmptyStrings() throws JsonProcessingException {
        SectionElementProps props = new SectionElementProps(UUID.randomUUID(), "", "", "", "", "", "", "");
        String jsonString = objectMapper.writeValueAsString(props);
        SectionElementProps deserializedProps = objectMapper.readValue(jsonString, SectionElementProps.class);
        assertEquals("", deserializedProps.getKey());
        assertEquals("", deserializedProps.getText());
        assertEquals("", deserializedProps.getWrapperStyle());
        assertEquals("", deserializedProps.getTextStyle());
        assertEquals("", deserializedProps.getInputStyle());
        assertEquals("", deserializedProps.getUrl());
        assertEquals("", deserializedProps.getStyle());
    }

    private SectionElementProps createSampleSectionElementProps() {
        return new SectionElementProps(UUID.randomUUID(), "myKey", "My Text", "wrapperStyle", "textStyle", "inputStyle", "http://example.com", "style");
    }
}