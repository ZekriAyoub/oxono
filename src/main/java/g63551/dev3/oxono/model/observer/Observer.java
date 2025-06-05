package g63551.dev3.oxono.model.observer;

/**
 * Interface for observers that are notified when there is an update.
 * Implementing classes should define the behavior for responding to changes.
 */
public interface Observer {

    /**
     * Called to notify the observer of an update.
     */
    public void update();
}
