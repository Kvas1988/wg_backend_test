package com.example.wg_backend;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class WgBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPingPage() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/ping"))
                .andReturn()
                .getResponse();

        assertEquals(200, response.getStatus());
        assertTrue(response.getContentAsString().contains("Cats Service. Version"));
    }

    @Test
    void testGetAllCats() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/cats"))
                .andReturn()
                .getResponse();

        assertEquals(200, response.getStatus());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Tihon", "Marfa");
    }

    // TODO: tests with offset and limits.
    @Test
    void testGetAllCatsOffset() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/cats?offset=3&limit=10"))
                .andReturn()
                .getResponse();

        assertEquals(200, response.getStatus());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("\"pageSize\":10,", "\"offset\":3,");
    }

    @Test
    void testGetAllCatsFilterOffset() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/cats?offset=0&limit=1&attribute=color&order=ASC"))
                .andReturn()
                .getResponse();

        assertEquals(200, response.getStatus());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("\"pageSize\":1,", "\"offset\":0,");
        assertThat(response.getContentAsString()).contains("black");
        assertThat(response.getContentAsString()).doesNotContain("red & black & white");
    }

    @Test
    void testGetAllCatsWrongAttribute() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/cats?attribute=paws&order=ASC"))
                .andReturn()
                .getResponse();

        assertEquals(422, response.getStatus());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
    }

    @Test
    void testCreateCatPositive() throws Exception {
        MockHttpServletResponse responsePost = mockMvc.
                perform(
                        post("/cat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("  {\n"
                                        + "    \"name\": \"Kvas\",\n"
                                        + "    \"color\": \"red & white\",\n"
                                        + "    \"tail_length\": 15,\n"
                                        + "    \"whiskers_length\": 12\n"
                                        + "  }")
                )
                .andReturn()
                .getResponse();

        assertEquals(200, responsePost.getStatus());

        MockHttpServletResponse response = mockMvc
                .perform(get("/cats"))
                .andReturn()
                .getResponse();

        assertEquals(200, response.getStatus());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("\"name\":\"Kvas\"");
    }

    @Test
    void testCreateCatNegative() throws Exception {

        // negative number in tail_length
        MockHttpServletResponse responsePost1 = mockMvc.
                perform(
                        post("/cat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("  {\n"
                                        + "    \"name\": \"Kvas\",\n"
                                        + "    \"color\": \"red & white\",\n"
                                        + "    \"tail_length\": -15,\n"
                                        + "    \"whiskers_length\": 12\n"
                                        + "  }")
                )
                .andReturn()
                .getResponse();

        assertEquals(422, responsePost1.getStatus());

        // wrong color enum
        MockHttpServletResponse responsePost2 = mockMvc.
                perform(
                        post("/cat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("  {\n"
                                        + "    \"name\": \"Kvas\",\n"
                                        + "    \"color\": \"lorem & ipsum\",\n"
                                        + "    \"tail_length\": 15,\n"
                                        + "    \"whiskers_length\": 12\n"
                                        + "  }")
                )
                .andReturn()
                .getResponse();

        assertEquals(422, responsePost2.getStatus());

        // String in length
        MockHttpServletResponse responsePost3 = mockMvc.
                perform(
                        post("/cat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("  {\n"
                                        + "    \"name\": \"Kvas\",\n"
                                        + "    \"color\": \"lorem & ipsum\",\n"
                                        + "    \"tail_length\": 15,\n"
                                        + "    \"whiskers_length\": \"big moustache\"\n"
                                        + "  }")
                )
                .andReturn()
                .getResponse();

        assertEquals(422, responsePost3.getStatus());
    }

}
