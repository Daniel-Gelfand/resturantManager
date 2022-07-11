package hit.projects.resturantmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.pojo.Table;
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
class TableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Get all tables success")
    @Test
    void getAllTablesTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/table"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Get specific table by id success")
    @Test
    void getTableTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/table/1"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Get tables by their status success")
    @Test
    void GetTableByStatusTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/table/status/BUSY"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }


    @DisplayName("Create new table success")
    @Test
    void createNewTableTestSuccess() throws Exception {
        Table table = Table.builder().tableNumber(5).tableStatus(TableStatus.AVAILABLE).build();


        mockMvc.perform(MockMvcRequestBuilders.post("/table")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(table)))
                .andDo(print()).andExpect(status().isCreated()).andReturn();
    }

    @DisplayName("Create new table already exits")
    @Test
    void createNewTableTestConflict() throws Exception {
        Table table = Table.builder().tableNumber(1).tableStatus(TableStatus.AVAILABLE).build();


        mockMvc.perform(MockMvcRequestBuilders.post("/table")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(table)))
                .andDo(print()).andExpect(status().isConflict()).andReturn();
    }


    //TODO: test not working but yes delete the table from database
    @DisplayName("Delete table by id success")
    @Test
    void deleteTableTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/table/1"))
                .andDo(print()).andExpect(status().isAccepted());
    }

    @DisplayName("Get table dto info success")
    @Test
    void getTableInfoTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/table/2/info"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Get all tables dto info success")
    @Test
    void getAllTablesInfoTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/table/info"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }
}