package hit.projects.resturantmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Get menu test success")
    @Test
    void getMenuTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/menu"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Get menu by price test success")
    @Test
    void getSingleMenuItemByPriceTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/menu/123"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Get all category test success")
    @Test
    void getAllCategoryTestSuccess() throws Exception {
        //TODO: category = ?
        mockMvc.perform(MockMvcRequestBuilders.get("/menu/category/{category}"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Get by category and price test success")
    @Test
    void getByCategoryAndPriceTestSuccess() throws Exception {
        //TODO:
        mockMvc.perform(MockMvcRequestBuilders.get("/menu/search/"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @Test
    void getSingleMenuItem() {

    }

    @Test
    void menuItemInfo() {

    }

    @Test
    void allMenuItemInfo() {

    }

    @Test
    void updateMenuItem() {

    }

    @Test
    void newMenuItem() {

    }


    @DisplayName("delete MenuItem test success")
    @Test
    public void deleteMenuItemTestSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/menu/?"))
                .andDo(print()).andExpect(status().isAccepted());
    }

    @DisplayName("delete MenuItem test NotFound")
    @Test
    public void deleteMenuItemTestNotFound() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/menu/?"))
                .andDo(print()).andExpect(status().isNotFound());
    }
}