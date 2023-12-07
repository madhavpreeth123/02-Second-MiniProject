package in.madhav.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.madhav.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer>{

}
