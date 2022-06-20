package hit.projects.resturantmanager.exception;

public class WaiterException extends RuntimeException{

    public WaiterException(String name) {
        super("There are not Menuitem corresponding to name = " + name);
    }
    public WaiterException(boolean isOnDuty) {
        super(isOnDuty ? "There is no one in the duty" : "All Waiters are in duty");
    }
}
