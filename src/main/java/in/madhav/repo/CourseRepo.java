package in.madhav.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.madhav.entity.CoursesEntity;


public interface CourseRepo extends JpaRepository<CoursesEntity, Integer> {

}
