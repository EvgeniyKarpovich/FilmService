package by.karpovich.filmSevice.jpa.converter;

import by.karpovich.filmSevice.jpa.model.RewardForActor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Converter
public class RewardForActorConverter implements AttributeConverter<List<RewardForActor>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<RewardForActor> rewardForActors) {
        if (rewardForActors.isEmpty()) {
            return null;
        }
        return rewardForActors.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public List<RewardForActor> convertToEntityAttribute(String s) {
        if (s != null) {
            String[] strings = s.split(SEPARATOR);
            return Arrays.stream(strings)
                    .map(RewardForActor::valueOf)
                    .collect(toList());
        } else {
            return null;
        }
    }
}
