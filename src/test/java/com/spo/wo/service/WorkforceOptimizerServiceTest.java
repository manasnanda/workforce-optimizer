package com.spo.wo.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.spo.wo.datatransferobject.WorkforceOptimizerDTO;
import com.spo.wo.exception.InvalidRoomSizeException;
import com.spo.wo.exception.RoomSizeExceededException;
import com.spo.wo.model.Workforce;

/**
 * @author Manas Ranjan Nanda
 * This class has test cases for WorkforceOptimizerService
 * ****/
@RunWith(SpringRunner.class)
public class WorkforceOptimizerServiceTest
{
    
    WorkforceOptimizerService woService;
    
    @Before
    public void setUp()
    {
        woService = new WorkforceOptimizerServiceImpl();
    }
    @Test
    public void optimiseWorkforce() throws Exception
    {
        List<Workforce> expectredWorkforces = new ArrayList<Workforce>();
        expectredWorkforces.add(new Workforce(3, 1));
        expectredWorkforces.add(new Workforce(1, 2));
        expectredWorkforces.add(new Workforce(2, 0));
        expectredWorkforces.add(new Workforce(1, 3));
        
        WorkforceOptimizerDTO woDTO = WorkforceOptimizerDTO.newBuilder()
            .setJunior(6)
            .setSenior(10)
            .setRooms(new int []{35,21,17,28})
            .createWORequestDTO();
        
        List<Workforce> actualWorkforces = woService.optimiseWorkforce(woDTO); 
        assertEquals(expectredWorkforces.get(0).getSenior(), actualWorkforces.get(0).getSenior());
    }
    
    @Test(expected=InvalidRoomSizeException.class)
    public void optimiseWorkforce_InvalidRoomSizeException() throws Exception
    {
        WorkforceOptimizerDTO woDTO = WorkforceOptimizerDTO.newBuilder()
            .setJunior(6)
            .setSenior(10)
            .setRooms(new int []{35,21,17,-1})
            .createWORequestDTO();
        
        woService.optimiseWorkforce(woDTO); 
    }
    @Test(expected=RoomSizeExceededException.class)
    public void optimiseWorkforce_RoomSizeExceededException() throws Exception
    {
        WorkforceOptimizerDTO woDTO = WorkforceOptimizerDTO.newBuilder()
            .setJunior(6)
            .setSenior(10)
            .setRooms(new int []{35,21,17,100})
            .createWORequestDTO();
        
        woService.optimiseWorkforce(woDTO); 
    }
}
