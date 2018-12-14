package design.exam;

import design.exam.Model.Equipment;
import design.exam.Model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface equipmentRepository extends CrudRepository<Equipment, Long> {
    List<Equipment> findAll();
    List<Equipment> findByOwner(Person person);
}
