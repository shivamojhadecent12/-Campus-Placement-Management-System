package com.SpringBoot.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.SpringBoot.Entities.Students;

@Repository
public interface StudentsRepo extends JpaRepository<Students, Long> {
	Students findBySEmail(String SEmail);

	@Query("SELECT s FROM Students s WHERE s.SEmail = :email AND s.s_password = :password")
	Students findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	List<Students> findByBranch(String branch);
}
