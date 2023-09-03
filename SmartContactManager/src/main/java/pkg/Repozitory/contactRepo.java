package pkg.Repozitory;


 
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
 
 import pkg.entities.contact;
import pkg.entities.usar;
@Repository
public interface contactRepo  extends JpaRepository<contact,Integer>{
	/*just like 'List', 'Page' also store multiple variables but along with value it stores the page's position after certain values
    'Pageable' stores values per page & current page	*/
	@Query("select c from contact as c where c.usr.id=:id")//the usr.id is the id of user in contact class
 	public Page<contact> findcontactsByUsr(@Param("id")int id,Pageable Pageable);
	
//	@Query("select c from contact as c where c.usr.id=:id")
//	public List<contact> findcontactsByUsr(@Param("id")int id);
 	
 	
 	//below is part of searching featur
 	//the word 'Containg' will search even for little part of the  input, if the name has 's' it will show the result
// 	public List<contact> findByNameContainingAndUser(String krywords,usar usar);
	public List<contact> findByEmailContainingAndUsr(String email ,usar usar );
 	

}
