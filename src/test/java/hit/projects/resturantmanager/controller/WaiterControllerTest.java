package hit.projects.resturantmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hit.projects.resturantmanager.pojo.Waiter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WaiterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Get all waiters success")
    @Test
    void getAllWaitersTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/waiter"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Get specific waiter by id success")
    @Test
    void getWaiterTestSuccess() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders.get("/waiter/318324258"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Get all waiters duty status success")
    @Test
    void getDutyStatusTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/waiter/duty/true"))
                .andDo(print()).andExpect(status().isOk()).andReturn();

    }

    @DisplayName("Update specific waiter success")
    @Test
    void updateWaiterTestSuccess() throws Exception {
        Waiter waiter = new Waiter(318324258, "Matan", "Bar", 9000.0, 0.0, true);
        mockMvc.perform(MockMvcRequestBuilders.put("/waiter/209381773")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(waiter)))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }


    //TODO: problem with this one. (i think the problem is because every waiter have a table -> [--- WATCH MongoConfigcuration.class ---]
    @DisplayName("add waiter test success")
    @Test
    void addNewWaiterTestSuccess() throws Exception {

        Waiter waiter = new Waiter(202380773,"Daniel","Gelfand",9000.0,150.5,true);

        mockMvc.perform(MockMvcRequestBuilders.post("/waiter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(waiter)))
                .andDo(print()).andExpect(status().isCreated()).andReturn();
    }

    @DisplayName("Delete waiter test success")
    @Test
    void deleteWaiterTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/waiter/313324258"))
                .andDo(print()).andExpect(status().isAccepted());
    }

    @DisplayName("Delete waiter test not found")
    @Test
    void deleteWaiterTestNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/waiter/5555"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @DisplayName("Get all waiters that is on duty or not, dto info success")
    @Test
    void getAllWaitersOnDutyInfoTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/waiter/info/true"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Get specific waiter dto info success")
    @Test
    void waiterInfoTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/waiter/318324258/info"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Get all waiters dto info success")
    @Test
    void allWaitersInfoTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/waiter/info"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }
}