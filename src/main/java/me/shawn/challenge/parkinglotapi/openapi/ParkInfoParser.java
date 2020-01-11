package me.shawn.challenge.parkinglotapi.openapi;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;

import java.util.Map;

class ParkInfoParser {
    private ModelMapper modelMapper;

    public ParkInfoParser(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
    }

    ParkInfoDTO parse(Map<String, Object> source) {
        ParkInfoDTO destination = new ParkInfoDTO();
        modelMapper.map(source, destination);

        return destination;
    }
}
