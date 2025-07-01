package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class EmployeeDao implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Employee> employees;
    
    public EmployeeDao() {
        this.employees = new ArrayList<>();
        System.out.println("[EmployeeDao] Inicializado");
    }
    
    public void create(Employee employee) {
        if (employee != null) {
            employees.add(employee);
            System.out.println("[EmployeeDao] Empleado creado: " + employee.getName() + " (Tipo: " + employee.getType() + ")");
        }
    }
    
    public ArrayList<Employee> getAll() {
        return new ArrayList<>(employees);
    }
    
    public ArrayList<Employee> getChefs() {
        ArrayList<Employee> chefs = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.isChef()) {
                chefs.add(emp);
            }
        }
        System.out.println("[EmployeeDao] Chefs encontrados: " + chefs.size());
        return chefs;
    }
    
    public Employee getById(int id) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        return null;
    }
    
    public boolean update(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == employee.getId()) {
                employees.set(i, employee);
                System.out.println("[EmployeeDao] Empleado actualizado: " + employee.getName());
                return true;
            }
        }
        return false;
    }
    
    public boolean delete(int id) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                Employee removed = employees.remove(i);
                System.out.println("[EmployeeDao] Empleado eliminado: " + removed.getName());
                return true;
            }
        }
        return false;
    }
    
    public int getNextId() {
        int maxId = 0;
        for (Employee emp : employees) {
            if (emp.getId() > maxId) {
                maxId = emp.getId();
            }
        }
        return maxId + 1;
    }
    
    // Métodos para serialización
    public ArrayList<Employee> getInternalList() {
        return employees;
    }
    
    public void setInternalList(ArrayList<Employee> employees) {
        this.employees = employees != null ? employees : new ArrayList<>();
        System.out.println("[EmployeeDao] Lista interna establecida con " + this.employees.size() + " empleados");
    }
    
    public int size() {
        return employees.size();
    }
    
    public boolean isEmpty() {
        return employees.isEmpty();
    }
}
