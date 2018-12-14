package design.exam.Repository;

import design.exam.Model.Equipment;
import design.exam.Model.Person;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends CrudRepository<Equipment, Long> {
    List<Equipment> findAll();
    List<Equipment> findTop4ByOrderByIdDesc();
    List<Equipment> findAll(Specification specification);
    List<Equipment> findByOwner(Person person);
}
