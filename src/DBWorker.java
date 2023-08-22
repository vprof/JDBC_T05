import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBWorker {

    private final String URL = "jdbc:mysql://localhost:3306/myjoinsdb";
    private final String LOGIN = "root";
    private final String PASSWORD = "root";

    public DBWorker() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver has been loaded!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void executeQueries() {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.createStatement();

            List<Employees> employees1 = new ArrayList<>();
            ResultSet resultSet1 = statement.executeQuery("select employees.name, employees.phone, \n" +
                    "\t  (select personalinfo.address \n" +
                    "\t   from personalinfo \n" +
                    "\t   where personalinfo.e_id = employees.e_id) as address\n" +
                    "  from employees;");
            while (resultSet1.next()) {
                String name = resultSet1.getString("name");
                String phone = resultSet1.getString("phone");
                Employees employer= new Employees(name, phone);
                employees1.add(employer);
            }
            System.out.println("Contacts:");
            for (Employees employee:employees1) {
                System.out.println(employee.getName() + " " + employee.getPhone());
            }
            System.out.println("---------------------");

            List<Employees> employees2 = new ArrayList<>();
            ResultSet resultSet2 = statement.executeQuery("select employees.e_id as ID, employees.name Name, personalinfo.birthday\n" +
                    "  from employees\n" +
                    "  join personalinfo on employees.e_id = personalinfo.e_id\n" +
                    "\tWHERE employees.e_id IN (\n" +
                    "\t  SELECT e_id\n" +
                    "\t  FROM personalinfo\n" +
                    "\t  WHERE marital_status = 'single'\n" +
                    "\t);");
            while (resultSet2.next()) {
                String name = resultSet2.getString("Name");
                LocalDate bd = LocalDate.parse(resultSet2.getString("birthday"));
                Employees employer= new Employees(name, bd);
                employees2.add(employer);
            }
            System.out.println("Singles:");
            for (Employees employee:employees2) {
                System.out.println(employee.getName() + " " + employee.getBirthday());
            }
            System.out.println("---------------------");

            List<Employees> employees3 = new ArrayList<>();
            ResultSet resultSet3 = statement.executeQuery("select employees.name, personalinfo.birthday, employees.phone\n" +
                    "  from employees\n" +
                    "  inner join personalinfo on employees.e_id = personalinfo.e_id\n" +
                    "  where employees.e_id in \n" +
                    "    (select e_id \n" +
                    "     from salary \n" +
                    "     where job_title = 'PM');");
            while (resultSet3.next()) {
                String name = resultSet3.getString("Name");
                LocalDate bd = LocalDate.parse(resultSet3.getString("birthday"));
                String phone = resultSet3.getString("phone");
                Employees employer= new Employees(name, bd, phone);
                employees3.add(employer);
            }
            System.out.println("PM's:");
            for (Employees employee:employees3) {
                System.out.println(employee.getName() + " " + employee.getBirthday() + " " + employee.getPhone());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}