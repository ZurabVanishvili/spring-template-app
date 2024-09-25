package net.template.server.user.service;


import net.template.server.user.entity.User;
import net.template.server.user.mapper.UserMapper;
import net.template.server.user.model.UserDTO;
import net.template.server.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserDTO findById(long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    public List<UserDTO> loadAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return repository.findAll(pageable).map(mapper::toDTO).stream().toList();
    }


    public UserDTO save(UserDTO dto) {
        User user = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(user));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
