package com.I0Idigital.demo.repository;

import java.util.Optional;

import com.I0Idigital.demo.domain.Role;
import com.I0Idigital.demo.domain.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
