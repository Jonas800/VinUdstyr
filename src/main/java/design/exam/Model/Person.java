package design.exam.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public abstract class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String telephoneNumber;
    private String address;
    private Integer zipcode;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Equipment> favoriteEquipment;

    public Person() {
    }


    public List<Equipment> getFavoriteEquipment() { return favoriteEquipment; }

    public void setFavoriteEquipment(List<Equipment> favoriteEquipment) { this.favoriteEquipment = favoriteEquipment; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addTofavorits(Equipment e){
        favoriteEquipment.add(e);
    }
}
