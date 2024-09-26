package net.template.server.user.service;


import net.template.server.user.entity.User;
import net.template.server.user.mapper.UserMapper;
import net.template.server.user.model.UserDTO;
import net.template.server.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

        if (user.getId() <= 0) {
            Optional<User> userOptional = repository.findByLoginAndIdNot(user.getLogin(), user.getId());
            if (userOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("User with login %s already exists", user.getLogin()));
            }
        }

        return mapper.toDTO(repository.save(user));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
