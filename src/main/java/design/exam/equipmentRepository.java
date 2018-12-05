package design.exam;

import design.exam.Model.Equipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface equipmentRepository extends CrudRepository<Equipment, Long> {
    List<Equipment> findAll();
}
