package design.exam.Model;

import javax.persistence.Entity;

@Entity
public class User extends Person{
    private Boolean isApproved;

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
}
