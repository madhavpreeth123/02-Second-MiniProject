package in.madhav.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.madhav.entity.UserDetailsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDetailsEntity, Integer> {

	public UserDetailsEntity findByEmail(String email);
	public UserDetailsEntity findByEmailAndPwd(String email,String pwd);
}
