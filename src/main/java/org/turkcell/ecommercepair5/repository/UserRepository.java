package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.turkcell.ecommercepair5.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {



}
