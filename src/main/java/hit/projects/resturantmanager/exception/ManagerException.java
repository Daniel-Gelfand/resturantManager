package hit.projects.resturantmanager.exception;

import hit.projects.resturantmanager.pojo.MenuItem;

public class ManagerException extends RuntimeException{

    public ManagerException(String name) {
        super("There are not manager corresponding to name = " + name);
    }

    public ManagerException(int personalId) {
        super("There are not manager corresponding to id = " + personalId);
    }

}
