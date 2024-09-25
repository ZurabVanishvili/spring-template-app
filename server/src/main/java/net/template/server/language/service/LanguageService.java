package net.template.server.language.service;

import net.template.server.language.entity.Language;
import net.template.server.language.mapper.LanguageMapper;
import net.template.server.language.model.LanguageDTO;
import net.template.server.language.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LanguageService {


    private final LanguageRepository repository;

    private final LanguageMapper mapper;

    public LanguageService(LanguageRepository repository, LanguageMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public LanguageDTO findById(long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow(() -> new RuntimeException("not found")));
    }

    public LanguageDTO save(LanguageDTO dto) {
        Language language = mapper.toEntity(dto);
        if (language.getId() > 0) {
            Optional<Language> languageOptional = repository.findById(language.getId());
            if (languageOptional.isPresent()) {
                Language existing = languageOptional.get();
                language.setVersion(existing.getVersion());
            }
        }
        return mapper.toDTO(repository.save(language));

    }

    public List<LanguageDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
