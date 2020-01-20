package pl.coderslab.charity.services;


import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class Mapper<TIn, TOut> {

//    private TIn tIn;
//    private TOut tOut;
//    private Class<TIn> tInType;
//    private Class<TOut> tOutType;
//
//    public Mapper(Class<TIn> tInType, Class<TOut> tOutType) {
//        this.tInType = tInType;
//        this.tOutType = tOutType;
//    }

    public TOut mapObj (TIn tIn, TOut tOut, String method) {
        if (!Arrays.asList("LOOSE", "STANDARD", "STRICT").contains(method.toUpperCase())) { return null; }

        ModelMapper modelMapper = new ModelMapper();
        switch (method.toUpperCase()) {
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
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Mapper.mapObj tIn: {}", tIn);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Mapper.mapObj tIn.getClass(): {}", tIn.getClass());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Mapper.mapObj (Type) tIn.getClass(): {}", (Type) tIn.getClass());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Mapper.mapObj tOut: {}", tOut);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Mapper.mapObj tOut.getClass(): {}", tOut.getClass());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Mapper.mapObj (Type) tOut.getClass(): {}", (Type) tOut.getClass());
//        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Mapper.mapObj tOutType: {}", tOutType);
//        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Mapper.mapObj tOutType.getClass(): {}", tOutType.getClass());
//        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Mapper.mapObj tOutType.getClasses(): {}", tOutType.getClasses());
//        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Mapper.mapObj tOutType.getClassLoader(): {}", tOutType.getClassLoader());
//        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Mapper.mapObj tOutType.getComponentType(): {}", tOutType.getComponentType());
        return modelMapper.map(tIn, (Type) tOut.getClass());
    }

    public List<TOut> mapList (List<TIn> tInList, TOut tOut, String method) {
        if (!Arrays.asList("LOOSE", "STANDARD", "STRICT").contains(method.toUpperCase())) { return null; }

        List<TOut> tOutList = new ArrayList<>();
        for (TIn tIn : tInList) {
            tOutList.add(mapObj(tIn, tOut, method));
        }

        return tOutList;
    }

}
