package by.karpovich.filmSevice.jpa.converter;

import by.karpovich.filmSevice.jpa.model.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class RolesConverter implements AttributeConverter<Set<Role>, String> {
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Set<Role> roles) {
        if (roles.isEmpty()) {
            return null;
        }
        return roles.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public Set<Role> convertToEntityAttribute(String s) {
        if (s != null) {
            String[] strings = s.split(SEPARATOR);
            return Arrays.stream(strings)
                    .map(Role::valueOf)
                    .collect(Collectors.toSet());
        } else {
            return null;
        }
    }
}
