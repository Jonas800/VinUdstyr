package design.exam.Helpers;

import design.exam.Model.Equipment;
import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification {

    public static Specification<Equipment> doesFieldContain(String parameter, String targetField) {
        return (Specification<Equipment>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(targetField), "%" + parameter + "%");
    }

    public static Specification<Equipment> doesForeignKeyContain(String parameter, String targetField, String joinTable) {
        return (Specification<Equipment>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.join(joinTable).get(targetField), "%" + parameter + "%");
    }
}
