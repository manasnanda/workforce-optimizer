package com.spo.wo.datatransferobject;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spo.wo.model.Workforce;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class WorkforceOptimizerDTO
{
    @NotEmpty(message = "Rooms can not be empty!")
    private int [] rooms;
    @Min(value=1,message = "Senior Capacity can not be zero or null!")
    private int senior;
    @Min(value=0,message = "Junior Capacity can not be negative or null!")
    private int junior;
    
    private List<Workforce> workforces; 
    
    
    
    private WorkforceOptimizerDTO()
    {
        
    }

    private WorkforceOptimizerDTO(List<Workforce> workforces)
    {
        super();
        this.workforces = workforces;
    }

    public WorkforceOptimizerDTO(int[] rooms, int senior, int junior)
    {
        this.rooms = rooms;
        this.senior = senior;
        this.junior = junior;
    }

    public int [] getRooms()
    {
        return rooms;
    }

    public int getSenior()
    {
        return senior;
    }

    public int getJunior()
    {
        return junior;
    }

    public List<Workforce> getWorkforces()
    {
        return workforces;
    }

    public static WorkforceOptimizerDTOBuilder newBuilder()
    {
        return new WorkforceOptimizerDTOBuilder();
    }
    
    public static class WorkforceOptimizerDTOBuilder
    {
        List<Workforce> workforces;
        int [] rooms;
        int senior;
        int junior;
        
        public WorkforceOptimizerDTOBuilder setWorkforces(List<Workforce> workforces)
        {
            this.workforces=workforces;
            return this;
        }
        public WorkforceOptimizerDTOBuilder setRooms(int [] rooms)
        {
             this.rooms = rooms;
             return this;
        }
        public WorkforceOptimizerDTOBuilder setSenior(int senior)
        {
             this.senior = senior;
             return this;
        }
        public WorkforceOptimizerDTOBuilder setJunior(int junior)
        {
             this.junior = junior;
             return this;
        }
        public WorkforceOptimizerDTO createWorkforceOptimizerDTO()
        {
            return new WorkforceOptimizerDTO(workforces);
        }
        public WorkforceOptimizerDTO createWORequestDTO()
        {
            return new WorkforceOptimizerDTO(rooms,senior,junior);
        }
    }
    
    @Override
    public String toString()
    {
        return "Rooms:"+Arrays.toString(this.rooms)+" Senior:"+this.senior+" Capacity:"+this.junior;
    }
    
}
