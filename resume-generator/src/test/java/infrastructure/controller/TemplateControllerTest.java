package infrastructure.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import webapp.resumegenerator.domain.service.TemplateService;
import webapp.resumegenerator.domain.model.Template;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import webapp.resumegenerator.ResumeGeneratorApplication;
import webapp.resumegenerator.domain.controller.TemplateController;

/**
 * Тестовый класс для проверки функциональности {@link TemplateController}.
 */
@SpringBootTest(classes = ResumeGeneratorApplication.class)
@AutoConfigureMockMvc
public class TemplateControllerTest {
    /**
     * Сервис для работы с шаблонами.
     */
    @Mock
    private TemplateService templateService;

    /**
     * Экземпляр контроллера {@link TemplateController}, в который внедряется мокированный сервис.
     */
    @InjectMocks
    private TemplateController templateController;

    /**
     * Объект для выполнения HTTP-запросов и проверки ответов контроллера.
     */
    private MockMvc mockMvc;

    /**
     * Инициализация MockMvc перед выполнением каждого теста.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(templateController).build();
    }

    /**
     * Тест на проверку, что GET-запрос возвращает пустой список.
     *
     * @throws Exception при возникновении ошибки выполнения запросв.
     */
    @Test
    @DisplayName("Проверка, что /templates возвращает пустой список если шаблонов не существует")
    public void testGetTemplates_emptyList() throws Exception {
        when(templateService.getAllTemplates()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/templates")).andExpect(status().isOk())
      .andExpect(content().json("[]")).andDo(MockMvcResultHandlers.print());
    }

    /**
     * Тест на проверку, что GET-запрос возвращает не пустой список.
     *
     * @throws Exception при возникновении ошибки выполнения запроса.
     */
    @Test
    @DisplayName("Проверка, что /templates возвращает непустой список")
    public void testGetTemplates_nonEmptyList() throws Exception {
        Template template1 = new Template("Template1", "Description1", "Content1");
        Template template2 = new Template("Template2", "Description2", "Content2");
        List<Template> templates = List.of(template1, template2);
        when(templateService.getAllTemplates()).thenReturn(templates);
        mockMvc.perform(get("/templates"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].name").value("Template1"))
      .andExpect(jsonPath("$[1].name").value("Template2"))
                .andDo(MockMvcResultHandlers.print());
    }


    /**
     * Тест на проверку, что GET-запрос возвращает ошибку, если шаблон с указанным id не найден.
     *
     * @throws Exception Исключение, возникающее при выполнении запроса.
     */
    @Test
    @DisplayName("/templates/{id} возвращает ошибку,если шаблон не найден")
    public void testGetTemplateById() throws Exception {
        mockMvc.perform(get("/templates/{id}", UUID.randomUUID())).andExpect(status().isNotFound());
    }

    /**
     * Тест на проверку, что POST-запрос создает новый шаблон и возвращает его с присвоенным ему id.
     *
     * @throws Exception исключение, возникающее при выполнении запрос.
     */
    @Test
    @DisplayName("Проверка что при создании нового шаблона возвращается созданный шаблон")
    public void testCreateTemplate() throws Exception {
        UUID generatedId = UUID.randomUUID();
        Template inputTemplate = new Template("Template1", "Description1", "Content1");
        Template savedTemplate = new Template("Template1", "Description1", "Content1");
        savedTemplate.setId(generatedId);
        when(templateService.createTemplate(any(Template.class))).thenReturn(savedTemplate);
        mockMvc.perform(
        MockMvcRequestBuilders.post("/templates").contentType(MediaType.APPLICATION_JSON)
          .content(
            "{\"name\":\"Template1\",\"description\":\"Description1\",\"content\":\"Content1\"}"))
      .andExpect(status().isCreated()) // Expect 201 Created
      .andExpect(jsonPath("$.name").value("Template1"))
      .andExpect(jsonPath("$.description").value("Description1"))
      .andExpect(jsonPath("$.id").value(generatedId.toString()))
                .andDo(MockMvcResultHandlers.print());
        verify(templateService, times(1)).createTemplate(any(Template.class));
    }

    /**
     * Тест на проверку, что DELETE-запрос выполняется успешно, при удалении шаблона.
     *
     * @throws Exception Исключение возникающее при удалении.
     */
    @Test
    @DisplayName("Проверка что при удалении шаблона по id возвращается статус 204")
    public void testDeleteTemplate() throws Exception {
        mockMvc.perform(delete("/templates/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Проверка что при создании новой версии шаблона создается такой же шаблон с версией, увеличенной на 1")
    public void testCreateNewTemplateVersion() throws Exception {
        UUID generatedId = UUID.randomUUID();
        Template template = new Template("Template1", "Description1", "Content1");
        template.setId(generatedId);
        Template newVersion = new Template("Template1", "Description1", "Content1");
        newVersion.setVersion(template.getVersion() + 1);
        newVersion.setId(UUID.randomUUID());
        when(templateService.getTemplateById(generatedId.toString())).thenReturn(template);
        when(templateService.createNewTemplateVersion(template)).thenReturn(newVersion);
        mockMvc.perform(MockMvcRequestBuilders.post("/templates/{id}/version", generatedId).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Template1\",\"description\":\"Description1\",\"content\":\"Content1\", \"version\":\"2\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Template1"))
                .andExpect(jsonPath("$.description").value("Description1"))
                .andExpect(jsonPath("$.content").value("Content1"))
                .andExpect(jsonPath("$.version").value(2))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Проверка, что при поиске всех версий шаблона возвращается список всех версий в порядке убывания")
    public void testGetAllTemplateVersions() throws Exception {
        UUID generatedId = UUID.randomUUID();
        Template template1 = new Template("Template1", "Description1", "Content1");
        Template template2 = new Template("Template1", "Description1", "Content1");
        Template template3 = new Template("Template1", "Description1", "Content1");
        template2.setVersion(2);
        template3.setVersion(3);
        List<Template> templates = List.of(template3, template2, template1);
        when(templateService.getTemplateById(generatedId.toString())).thenReturn(template1);
        when(templateService.findAllTemplateVersionsByName("Template1")).thenReturn(templates);
        mockMvc.perform(get("/templates/{id}/versions", generatedId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Template1"))
                .andExpect(jsonPath("$[1].name").value("Template1"))
                .andExpect(jsonPath("$[2].name").value("Template1"))
                .andExpect(jsonPath("$[0].version").value(3))
                .andExpect(jsonPath("$[1].version").value(2))
                .andExpect(jsonPath("$[2].version").value(1))
                .andDo(MockMvcResultHandlers.print());
    }


}