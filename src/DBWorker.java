import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBWorker {

    private final String URL = "jdbc:mysql://localhost:3306/myjoinsdb";
    private final String LOGIN = "root";
    private final String PASSWORD = "root";

    final String contacts_script = "select employees.name, employees.phone, \n" +
            "(select personalinfo.address from personalinfo\n" +
            "where personalinfo.e_id = employees.e_id) as address from employees;";

    final String single_birthday_script = "select employees.e_id as ID, employees.name Name, personalinfo.birthday from employees\n" +
            "join personalinfo on employees.e_id = personalinfo.e_id\n" +
            "WHERE employees.e_id IN (\n" +
            "SELECT e_id FROM personalinfo \n" +
            "WHERE marital_status = 'single');";

    final String manager_script = "select employees.name, personalinfo.birthday, employees.phone from employees\n" +
            "inner join personalinfo on employees.e_id = personalinfo.e_id\n" +
            "where employees.e_id in \n" +
            "(select e_id from salary \n" +
            "where job_title = 'PM');";

    public DBWorker() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver has been loaded!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getData(){
        try {
            getContacts();
            getSingleBirthdays();
            getManagers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getContacts(){
        List<Employees> contactList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement()){
            ResultSet contacts = statement.executeQuery(contacts_script);
            while (contacts.next()) {
                String name = contacts.getString("name");
                String phone = contacts.getString("phone");
                Employees employer = new Employees(name, phone);
                contactList.add(employer);
            }
            System.out.println("Contacts:");
            for (Employees employee:contactList) {
                System.out.println(employee.getName() + " " + employee.getPhone());
            }
            System.out.println("---------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getSingleBirthdays() throws SQLException {
        List<Employees> birthdaysList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement()){
            ResultSet birthdays = statement.executeQuery(single_birthday_script);
            while (birthdays.next()) {
                String name = birthdays.getString("Name");
                LocalDate bd = LocalDate.parse(birthdays.getString("birthday"));
                Employees employer = new Employees(name, bd);
                birthdaysList.add(employer);
            }

            System.out.println("Singles:");
            for (Employees employee:birthdaysList) {
                System.out.println(employee.getName() + " " + employee.getBirthday());
            }
            System.out.println("---------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getManagers() throws SQLException {
        List<Employees> managerList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement()){
            ResultSet managers = statement.executeQuery(manager_script);
            while (managers.next()) {
                String name = managers.getString("Name");
                LocalDate bd = LocalDate.parse(managers.getString("birthday"));
                String phone = managers.getString("phone");
                Employees employer= new Employees(name, bd, phone);
                managerList.add(employer);
            }

            System.out.println("PM's:");
            for (Employees employee:managerList) {
                System.out.println(employee.getName() + " " + employee.getBirthday() + " " + employee.getPhone());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}