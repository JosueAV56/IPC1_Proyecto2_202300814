
package Model;

public enum EmployeeType {
     CHEF("Cocinero"),
        ADMIN("Administrador");
        
        private final String description;
        
        private EmployeeType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
        
        @Override
        public String toString() {
            return description;
        }
}
