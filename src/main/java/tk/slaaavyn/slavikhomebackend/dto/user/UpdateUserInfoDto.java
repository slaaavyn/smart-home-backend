package tk.slaaavyn.slavikhomebackend.dto.user;

import tk.slaaavyn.slavikhomebackend.model.User;

import javax.validation.constraints.NotEmpty;

public class UpdateUserInfoDto {
    @NotEmpty(message = "firstName cannot be empty")
    private String firstName;

    @NotEmpty(message = "lastName cannot be empty")
    private String lastName;

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

    public User fromDto() {
        User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }

    @Override
    public String toString() {
        return "UpdateUserInfoDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
