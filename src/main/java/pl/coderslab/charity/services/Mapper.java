package pl.coderslab.charity.services;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Mapper<TIn, TOut> {

    /**
     * Generic method of converting/mapping object of TIn class to object of TOut class
     * Applied map method of ModelMapper class acc. strategy
     * @param tIn       - object of class TIn to be mapped to TOut class
     * @param tOut      - just empty class for parameter of map method of ModelMapper class
     * @param strategy  - strategy of map method of ModelMapper class
     * @return          - object of class TOut converted/mapped from TIn class
     */
    public TOut mapObj (TIn tIn, TOut tOut, String strategy) {
        // validation
        if (tIn == null || tOut == null) { return null; }
        if (!Arrays.asList("LOOSE", "STANDARD", "STRICT").contains(strategy.toUpperCase())) { return null; }

        ModelMapper modelMapper = new ModelMapper();
        switch (strategy.toUpperCase()) {
            case "LOOSE":
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                break;
            case "STANDARD":
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
                break;
            case "STRICT":
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
                break;
            default:
                return null;
        }
        return modelMapper.map(tIn, (Type) tOut.getClass());
    }

    /**
     * Generic method of converting/mapping list of objects of TIn class to ArrayList of objects of TOut class
     * Applied map method of ModelMapper class acc. strategy
     * @param tInList   - List of objects of Class TIn to be mapped
     * @param tOut      - just empty class for parameter of map method of ModelMapper class
     * @param strategy  - strategy of map method of ModelMapper class
     * @return          - ArrayList of objects of class TOut converted/mapped from list of objects of TIn class
     */
    public List<TOut> mapList (List<TIn> tInList, TOut tOut, String strategy) {
        // validation
        if (tInList == null || tOut == null) { return null; }
        if (!Arrays.asList("LOOSE", "STANDARD", "STRICT").contains(strategy.toUpperCase())) { return null; }

        List<TOut> tOutList = new ArrayList<>();
        for (TIn tIn : tInList) {
            tOutList.add(mapObj(tIn, tOut, strategy));
        }
        return tOutList;
    }

}
