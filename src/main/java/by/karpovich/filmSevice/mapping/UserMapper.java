package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.api.dto.UserDto;
import by.karpovich.filmSevice.jpa.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserModel mapFromDto(UserDto UserDto);

    UserDto mapFromEntity(UserModel music);

    List<UserModel> mapFromListDto(List<UserDto> UserDtoList);

    List<UserDto> mapFromListEntity(List<UserModel> musicList);
}
