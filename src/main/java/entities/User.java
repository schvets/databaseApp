package entities;

/**
 * Created by Aleksandr on 11.05.2017.
 */
public final class User {
    private String firstName;
    private String lastName;
    private String category;
    private String sex;

    public User() {
    }

    public User(String firstName, String lastName, String category, String sex) {
        if (firstName == null) {
            throw new NullPointerException("firstName is null");
        }
        if (lastName == null) {
            throw new NullPointerException("lastName is null");
        }
        if (category == null) {
            throw new NullPointerException("category is null");
        }
        if (sex == null) {
            throw new NullPointerException("sex is null");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.category = category;
        this.sex = sex;
    }

    public String getUserFirstName() {
        return firstName;
    }

    public String getUserLastName() {
        return lastName;
    }

    public String getUserCategory() {
        return category;
    }

    public String getUserSex() {
        return sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!firstName.equals(user.firstName)) {
            return false;
        }
        if (!lastName.equals(user.lastName)) {
            return false;
        }
        if (!category.equals(user.category)) {
            return false;
        }
        return sex.equals(user.sex);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + sex.hashCode();
        return result;
    }
}
