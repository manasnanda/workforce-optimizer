package com.spo.wo.controller;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spo.wo.controller.mapper.WorkforceOptimizerMapper;
import com.spo.wo.datatransferobject.WorkforceOptimizerDTO;
import com.spo.wo.exception.InvalidRoomSizeException;
import com.spo.wo.exception.RoomSizeExceededException;
import com.spo.wo.model.Workforce;
import com.spo.wo.service.WorkforceOptimizerService;

/**
 * @author Manas Ranjan Nanda
 * Workforce Optimization request will be routed by this controller
 * ***/

@RestController
@RequestMapping("v1/workforceoptimizer")
public class WorkfoceOptimizerController
{
    private static final Logger LOG = LoggerFactory.getLogger(WorkfoceOptimizerController.class);
    
    private WorkforceOptimizerService woService;
    
    @Autowired
    public WorkfoceOptimizerController(final WorkforceOptimizerService woService)
    {
        this.woService = woService;
    }
    
    @PostMapping
    public List<Workforce> optimizer(@Valid @RequestBody WorkforceOptimizerDTO woDTO ) throws RoomSizeExceededException, InvalidRoomSizeException
    {
        LOG.info("WorkfoceOptimizer Controller: Request:"+woDTO);
        WorkforceOptimizerDTO woRespDTO = WorkforceOptimizerMapper.makeWorkforceOptimizationDTO(woService.optimiseWorkforce(woDTO));
        return woRespDTO.getWorkforces();
    }
}
