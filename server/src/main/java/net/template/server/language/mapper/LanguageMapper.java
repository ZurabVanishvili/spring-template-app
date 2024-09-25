package net.template.server.language.mapper;

import net.template.server.language.entity.Language;
import net.template.server.language.model.LanguageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    Language toEntity(LanguageDTO language);

    LanguageDTO toDTO(Language language);
}

