package com.yellowfuture.thanku.network.form;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * Created by zuby on 2016. 7. 23..
 */
public class BaseForm {
    public static ModelMapper modelMapper = new ModelMapper();
    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
}
