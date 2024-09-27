package net.template.server.permission.repository;

import net.template.server.permission.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("SELECT p.code FROM SYS_PERMISSIONS p")
    List<String> findAllPermissionCodes();

    Optional<Permission> findByCodeAndIdNot(String code, long id);
}
