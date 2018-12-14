package design.exam.Model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String equipmentName;
    private Integer equipmentAge;
    private Integer priceFromNew;
    private String dependency;
    private String ownerComment;
    private Boolean availableForLoan;
    private String fileName;

    @Transient
    private Integer distance;

    @ManyToOne
    private Person owner;

    @ManyToOne
    private Person currentHolder;

    public Equipment(String equipmentName, int equipmentAge, int priceFromNew, String dependency, String ownerComment, boolean availableForLoan, Person currentHolder, Person owner) {
        this.equipmentName = equipmentName;
        this.equipmentAge = equipmentAge;
        this.priceFromNew = priceFromNew;
        this.dependency = dependency;
        this.ownerComment = ownerComment;
        this.availableForLoan = availableForLoan;
        this.currentHolder = currentHolder;
        this.owner = owner;
    }

    public Equipment() {
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEquipmentName() { return equipmentName; }

    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }

    public Integer getEquipmentAge() { return equipmentAge; }

    public void setEquipmentAge(Integer equipmentAge) { this.equipmentAge = equipmentAge; }

    public Integer getPriceFromNew() { return priceFromNew; }

    public void setPriceFromNew(Integer priceFromNew) { this.priceFromNew = priceFromNew; }

    public String getDependency() { return dependency; }

    public void setDependency(String dependency) { this.dependency = dependency; }

    public String getOwnerComment() { return ownerComment; }

    public void setOwnerComment(String ownerComment) { this.ownerComment = ownerComment; }

    public Boolean getAvailableForLoan() {
        return availableForLoan;
    }

    public void setAvailableForLoan(Boolean availableForLoan) {
        this.availableForLoan = availableForLoan;
    }

    public Person getCurrentHolder() { return currentHolder; }

    public void setCurrentHolder(Person currentHolder) { this.currentHolder = currentHolder; }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
