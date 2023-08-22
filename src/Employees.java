import java.time.LocalDate;

public class Employees {

    private String name;

    private LocalDate birthday;

    private String phone;

    public Employees(String name, LocalDate birthday, String phone) {
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
    }

    public Employees(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Employees(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }
}
