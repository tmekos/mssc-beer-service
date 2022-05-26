package meko.springframework.msscbeerservice.web.mappers;

import meko.springframework.msscbeerservice.domain.Beer;
import meko.springframework.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto BeerToBeerDto(Beer beer);

    Beer BeerDto(BeerDto dto);


}
