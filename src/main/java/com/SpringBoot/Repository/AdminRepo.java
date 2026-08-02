package com.SpringBoot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.SpringBoot.Entities.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
	Admin findByAEmail(String AEmail);

	@Query("SELECT a FROM Admin a WHERE a.AEmail = :email AND a.a_password = :password")
	Admin findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}
