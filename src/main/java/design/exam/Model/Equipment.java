package design.exam.Model;

import javax.persistence.*;

@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String equipmentName;
    private int equipmentAge;
    private int priceFromNew;
    private String dependency;
    private String ownerComment;
    private boolean availableForLoan;
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

    public int getEquipmentAge() { return equipmentAge; }

    public void setEquipmentAge(int equipmentAge) { this.equipmentAge = equipmentAge; }

    public int getPriceFromNew() { return priceFromNew; }

    public void setPriceFromNew(int priceFromNew) { this.priceFromNew = priceFromNew; }

    public String getDependency() { return dependency; }

    public void setDependency(String dependency) { this.dependency = dependency; }

    public String getOwnerComment() { return ownerComment; }

    public void setOwnerComment(String ownerComment) { this.ownerComment = ownerComment; }

    public boolean isAvailableForLoan() { return availableForLoan; }

    public void setAvailableForLoan(boolean availableForLoan) { this.availableForLoan = availableForLoan; }

    public Person getCurrentHolder() { return currentHolder; }

    public void setCurrentHolder(Person currentHolder) { this.currentHolder = currentHolder; }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
