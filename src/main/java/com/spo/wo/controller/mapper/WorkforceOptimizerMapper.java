package com.spo.wo.controller.mapper;

import java.util.List;

import com.spo.wo.datatransferobject.WorkforceOptimizerDTO;
import com.spo.wo.model.Workforce;

public class WorkforceOptimizerMapper
{

    public static WorkforceOptimizerDTO makeWorkforceOptimizationDTO(List<Workforce> optimiseWorkforce)
    {
        WorkforceOptimizerDTO.WorkforceOptimizerDTOBuilder woDTOBuilder = WorkforceOptimizerDTO.newBuilder()
            .setWorkforces(optimiseWorkforce);
        
        return woDTOBuilder.createWorkforceOptimizerDTO();
    }

}
