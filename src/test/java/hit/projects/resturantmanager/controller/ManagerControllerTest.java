package hit.projects.resturantmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hit.projects.resturantmanager.pojo.Manager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("get all managers test success")
    @Test
    public void getAllManagersTestSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/manager"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("get manager by id test success")
    @Test
    public void getManagerTestSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/209381773"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("get manager by bad id test BadRequest")
    @Test
    public void getManagerTestBadRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/5555"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @DisplayName("get manager by duty test Success")
    @Test
    public void getDutyStatusTestSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/duty/true"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("add manager test success")
    @Test
    public void addManagerTestSuccess() throws Exception{
        Manager manager = new Manager(123,"Y","B",80000.0,true);

        mockMvc.perform(MockMvcRequestBuilders.post("/manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(manager)))
                .andDo(print()).andExpect(status().isCreated()).andReturn();
    }

    @DisplayName("add manager test success")
    @Test
    public void addManagerTestBadRequest() throws Exception{
        Manager manager = new Manager(209381773,"Gilad","Shalit",8000.0,true);

        mockMvc.perform(MockMvcRequestBuilders.post("/manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(manager)))
                .andDo(print()).andExpect(status().isConflict());
    }

    @DisplayName("update manager test success")
    @Test
    public void updateManagerTestSuccess() throws Exception{
        Manager manager = new Manager(209381773,"Yarin","Shalit",8000.0,true);

        mockMvc.perform(MockMvcRequestBuilders.put("/manager/209381773")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(manager)))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("delete manager test success")
    @Test
    public void deleteManagerTestSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/manager/209444775"))
                .andDo(print()).andExpect(status().isAccepted());
    }

    @DisplayName("delete manager test NotFound")
    @Test
    public void deleteManagerTestNotFound() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/manager/5555"))
                .andDo(print()).andExpect(status().isNotFound());
    }
}