package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.api.dto.MusicDto;
import by.karpovich.filmSevice.jpa.model.MusicModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MusicMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    @Mapping(target = "films", ignore = true)
    MusicModel mapFromDto(MusicDto musicDto);

    MusicDto mapFromEntity(MusicModel music);

    List<MusicModel> mapFromListDto(List<MusicDto> musicDtoList);

    List<MusicDto> mapFromListEntity(List<MusicModel> musicList);


}
