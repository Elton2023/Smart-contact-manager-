package pkg.Repozitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pkg.entities.usar;
@Repository

public interface userRepo extends JpaRepository<usar,Integer> {

	@Query("select u from usar u Where u.email=:email")
	public usar getUsarByEmail(@Param("email")String email);
	

}