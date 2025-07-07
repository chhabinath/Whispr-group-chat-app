package com.chhabinath.userdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chhabinath.userdata.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	 Page<Users> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);
}
