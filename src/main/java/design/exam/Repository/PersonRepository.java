package design.exam.Repository;

import design.exam.Model.Equipment;
import design.exam.Model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    Person findByEmail(String email);
    List<Equipment> findAllByFavoriteEquipment(Person person);

}
