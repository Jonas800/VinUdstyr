package design.exam.Helpers;

import design.exam.Model.Equipment;
import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification {

    //Any String field
    final static String alwaysTrueTable = "equipmentName";

    public static Specification<Equipment> doesFieldContain(String parameter, String targetField) {
        return (Specification<Equipment>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(targetField), "%" + parameter + "%");
    }

    public static Specification<Equipment> doesFieldEqual(Boolean parameter, String targetField) {
        return (Specification<Equipment>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(targetField), parameter);
    }

    public static Specification<Equipment> doesFieldEqual(Integer parameter, String targetField) {
        if (parameter != null) {
            return (Specification<Equipment>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(targetField), parameter);
        } else {
            //spaghetti fix for Integer when receiving no values
            return doesFieldContain("", alwaysTrueTable);
        }
    }

    public static Specification<Equipment> isFieldBetween(Integer min, Integer max, String targetField) {
        return (Specification<Equipment>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get(targetField), min, max);
    }

    public static Specification<Equipment> doesForeignFieldContain(String parameter, String targetField, String joinTable) {
        return (Specification<Equipment>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.join(joinTable).get(targetField), "%" + parameter + "%");
    }

    public static Specification<Equipment> doesForeignFieldEqual(Integer parameter, String targetField, String joinTable) {
        if (parameter != null) {
            return (Specification<Equipment>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(joinTable).get(targetField), parameter);
        } else {
            //spaghetti fix for Integer when receiving no values
            return doesFieldContain("", alwaysTrueTable);
        }
    }

    public static Specification<Equipment> doesForeignKeyContain(Boolean parameter, String targetField, String joinTable) {
        return (Specification<Equipment>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.join(joinTable).get(targetField), "%" + parameter + "%");
    }
}
