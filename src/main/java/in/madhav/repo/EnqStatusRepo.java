package in.madhav.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.madhav.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
