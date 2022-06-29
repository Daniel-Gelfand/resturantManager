package hit.projects.resturantmanager.exception;

public class WaiterException extends RuntimeException{

    public WaiterException(int personalId) {
        super("Don't exist waiter with the personal id " + personalId);
    }
    public WaiterException(boolean isOnDuty) {
        super(isOnDuty ? "There is no one in the duty" : "All Waiters are in duty");
    }

}
