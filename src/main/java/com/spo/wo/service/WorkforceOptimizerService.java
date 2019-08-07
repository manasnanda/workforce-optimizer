package com.spo.wo.service;

import java.util.List;

import javax.validation.Valid;

import com.spo.wo.datatransferobject.WorkforceOptimizerDTO;
import com.spo.wo.exception.InvalidRoomSizeException;
import com.spo.wo.exception.RoomSizeExceededException;
import com.spo.wo.model.Workforce;

public interface WorkforceOptimizerService
{

    List<Workforce> optimiseWorkforce(@Valid WorkforceOptimizerDTO woDTO)throws RoomSizeExceededException,InvalidRoomSizeException;

}
