package net.template.server.user.repository;

import net.template.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM SYS_USERS u left join u.roles r left join r.permissions WHERE u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

    Optional<User> findByLoginAndIdNot(String login, long id);
}
