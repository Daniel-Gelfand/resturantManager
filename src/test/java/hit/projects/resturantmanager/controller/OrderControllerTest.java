package hit.projects.resturantmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hit.projects.resturantmanager.pojo.Manager;
import hit.projects.resturantmanager.pojo.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("get order by id test success")
    @Test
    public void getOrderTestSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/order/1"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("get order by bad id test BadRequest")
    @Test
    public void getOrderTestBadRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/order/5555"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @DisplayName("get all orders test success")
    @Test
    public void getAllManagersTestSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/order"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("get report by dates test success")
    @Test
    public void getReportByDatesTestSuccess() throws Exception{
        //TODO: Change the method before -> 2 objects
        mockMvc.perform(MockMvcRequestBuilders.get("/order"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("get order info test success")
    @Test
    public void getOrderInfoTestSuccess() throws Exception{
        //TODO: Change id
        mockMvc.perform(MockMvcRequestBuilders.get("/order/1/info"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("get order info test BadRequest")
    @Test
    public void getOrderInfoBadRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/order/5555/info"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @DisplayName("get all orders info test success")
    @Test
    public void getAllOrdersInfoTestSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/order/info"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }


    @DisplayName("add order test success")
    @Test
    public void addOrderTestSuccess() throws Exception{
        Order order = Order.builder().orderNumber(15).orderStatus(true)
                .orderList(null).tableId("5").build();

        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andDo(print()).andExpect(status().isCreated()).andReturn();
    }

    @DisplayName("add order test conflict")
    @Test
    public void addOderTestConflict() throws Exception{
        Order order = Order.builder().orderNumber(1).orderStatus(true)
                .orderList(null).tableId("5").build();

        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andDo(print()).andExpect(status().isConflict());
    }

    @DisplayName("update order test success")
    @Test
    public void updateManagerTestSuccess() throws Exception{
        Order order = Order.builder().orderNumber(30).orderStatus(true)
                .orderList(new ArrayList<>()).tableId("5").build();

        mockMvc.perform(MockMvcRequestBuilders.put("/order/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("add menu item to order list test success")
    @Test
    public void addMenuItemToOrderListrTestSuccess() throws Exception{

    }

    @DisplayName("delete order test success")
    @Test
    public void deleteOrderTestSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/3"))
                .andDo(print()).andExpect(status().isAccepted());
    }

    @DisplayName("delete order test NotFound")
    @Test
    public void deleteOrderTestNotFound() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/5555"))
                .andDo(print()).andExpect(status().isNotFound());
    }
}