package com.spo.wo.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.spo.wo.datatransferobject.WorkforceOptimizerDTO;
import com.spo.wo.exception.InvalidRoomSizeException;
import com.spo.wo.exception.RoomSizeExceededException;
import com.spo.wo.model.Workforce;

/**
 * @author Manas Ranjan Nanda
 * This class calculate and generate the optimal workforce capacity for given structure
 * 
 * **/

@Service
public class WorkforceOptimizerServiceImpl implements WorkforceOptimizerService
{
    private static final Logger LOG = LoggerFactory.getLogger(WorkforceOptimizerServiceImpl.class);


    @Override
    public List<Workforce> optimiseWorkforce(WorkforceOptimizerDTO woDTO) throws RoomSizeExceededException, InvalidRoomSizeException
    {
        LOG.info("Workforce Optimizer Service:");

        int[] rooms = woDTO.getRooms();

        // Validate the room size 
        for (int i : rooms)
        {
            if (i < 0)
                throw new InvalidRoomSizeException("Structure has invalid room size!");
            else if (i > 100)
                throw new RoomSizeExceededException("Structure has more than 100 Rooms");

        }

        return Arrays.stream(rooms).parallel().boxed().map(r -> staffOptimizer(r, woDTO))
            .collect(Collectors.toList());
    }


    private Workforce staffOptimizer(int roomSize, WorkforceOptimizerDTO woDTO)
    {
        // Start with maximum senior and no junior
        int seniorCount = (int) Math.ceil((double) roomSize / (double) woDTO.getSenior());
        int minCapacity = Math.min(woDTO.getSenior(), woDTO.getJunior());
        int juniorCount = 0;
        int minInterval = minInterval(woDTO.getSenior(), woDTO.getJunior());
        
        return optimize(woDTO, roomSize, minInterval, minCapacity, seniorCount, juniorCount, seniorCount, juniorCount);
    }

    /**
     * This function optimize the Workforce for given rooms, senior and junior staff capacity
     * 1. Start with maximum senior and zero junior staff
     * 2. Reduce senior and increase junior if over assignment with only senior staff and staff capacity is more than room size
     * 3. Reduce senior staff if over assignment with combination of senior & junior staff and staff capacity is more than room size
     * 4. Increase junior staff if under assignment with combination of senior & junior staff and staff capacity is less than room size
     * 5. None of the above then Optimal Staff capacity
     * 
     * ***/
    private Workforce optimize(WorkforceOptimizerDTO woDTO,
        int roomSize,
        int minInterval,
        int minCapacity,
        int seniorCount,
        int juniorCount,
        int newSeniorCount,
        int newJuniorCount)
    {
        
        Workforce workforce = null;

        int staffCapacity = (woDTO.getSenior() * newSeniorCount) + (woDTO.getJunior() * newJuniorCount);
        int remainingRoom = Math.abs(roomSize - staffCapacity);
        
        if (remainingRoom <= minInterval && staffCapacity >= roomSize)
        {           
            if (staffCapacity > roomSize 
                && newSeniorCount >= 1 
                && (woDTO.getSenior() * newSeniorCount) > roomSize) // Over assignment of only senior, reduce senior staff and increase junior staff
            {                   
                workforce = optimize(woDTO, roomSize, minInterval, minCapacity, newSeniorCount, newJuniorCount, newSeniorCount - 1, newJuniorCount + 1);                                
            }
            else // Optimal staff capacity
            {
                workforce = new Workforce(newSeniorCount, newJuniorCount);
            }
        }
        else if (remainingRoom > minCapacity && staffCapacity < roomSize) // Previous step was Optimal staff capacity
        {
            workforce = new Workforce(seniorCount, juniorCount);
        }
        else if (staffCapacity > roomSize && newSeniorCount > 1) // Over assignment, reduce senior staff
        {
            workforce = optimize(woDTO, roomSize, minInterval, minCapacity, newSeniorCount, newJuniorCount, newSeniorCount - 1, newJuniorCount);
        }
        else if (staffCapacity < roomSize)// Under assignment, need more junior staff
        {            
            workforce = optimize(woDTO, roomSize, minInterval, minCapacity, newSeniorCount, newJuniorCount, newSeniorCount, newJuniorCount + 1);
        }
        else // Optimal staff capacity
        {
            workforce = new Workforce(newSeniorCount, newJuniorCount);
        }

        return workforce;
    }


    private int minInterval(int x, int y)
    {
        if (y == 0)
        {
            return x;
        }

        return minInterval(y, x % y);
    }

}
