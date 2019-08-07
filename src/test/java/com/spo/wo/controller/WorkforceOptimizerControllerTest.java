package com.spo.wo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.spo.wo.datatransferobject.WorkforceOptimizerDTO;
import com.spo.wo.exception.InvalidRoomSizeException;
import com.spo.wo.exception.RoomSizeExceededException;
import com.spo.wo.model.Workforce;
import com.spo.wo.service.WorkforceOptimizerService;

/**
 * @author Manas Ranjan Nanda
 * This class has test cases for WorkforceOptimizerController
 * ****/
@RunWith(SpringRunner.class)
@WebMvcTest(value=WorkfoceOptimizerController.class)
@AutoConfigureMockMvc
public class WorkforceOptimizerControllerTest
{
    @Autowired
    MockMvc mockMvc;
    @MockBean
    WorkforceOptimizerService woService;
    @InjectMocks
    WorkfoceOptimizerController woController;
    
    private final String END_POINT = "/v1/workforceoptimizer";
    
    @Test
    public void optimizer_getOptimizedWorkforce() throws Exception
    {
        final String WO_REQ = "{\"rooms\": [35, 21, 17, 28], \"senior\": 10, \"junior\": 6 }";
        final String WO_RESULT = "[{\"senior\":3,\"junior\":1},{\"senior\":1,\"junior\":2},{\"senior\":2,\"junior\":0},{\"senior\":1,\"junior\":3}]";
        List<Workforce> workforces = new ArrayList<Workforce>();
        workforces.add(new Workforce(3, 1));
        workforces.add(new Workforce(1, 2));
        workforces.add(new Workforce(2, 0));
        workforces.add(new Workforce(1, 3));

        when(woService.optimiseWorkforce(Mockito.any(WorkforceOptimizerDTO.class))).thenReturn(workforces);
      
        
        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT)
            .content(WO_REQ)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(WO_RESULT));
    }
    
    @Test
    public void optimizer_InvalidRoomSizeException() throws Exception
    {
        final String WO_REQ = "{\"rooms\": [35, 21, 17, -1], \"senior\": 10, \"junior\": 6 }";
        
        when(woService.optimiseWorkforce(Mockito.any(WorkforceOptimizerDTO.class))).thenThrow(new InvalidRoomSizeException("Structure has invalid Room size!"));
          
        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT)
            .content(WO_REQ)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void optimizer_RoomSizeExceededException() throws Exception
    {
        final String WO_REQ = "{\"rooms\": [35, 21, 17, 100], \"senior\": 10, \"junior\": 6 }";
        
        when(woService.optimiseWorkforce(Mockito.any(WorkforceOptimizerDTO.class))).thenThrow(new RoomSizeExceededException("Structure has more than or equal to 100 Rooms!"));
          
        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT)
            .content(WO_REQ)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
}
