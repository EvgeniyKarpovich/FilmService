package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.api.dto.CountryDto;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CountryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    CountryModel mapFromDto(CountryDto countryDto);

    CountryDto mapFromEntity(CountryModel country);

    List<CountryModel> mapFromListDto(List<CountryDto> countryDtoList);

    List<CountryDto> mapFromListEntity(List<CountryModel> countries);
}