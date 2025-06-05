package g63551.dev3.oxono.model.observer;

/**
 * Interface for observable objects that maintain a list of observers.
 * It allows observers to register, remove, and be notified of changes.
 */
public interface Observable {

    /**
     * Registers an observer to receive updates.
     *
     * @param o the observer to register
     */
    void registerObserver(Observer o);

    /**
     * Removes an observer from the list of observers.
     *
     * @param o the observer to remove
     */
    void removeObserver(Observer o);

    /**
     * Notifies all registered observers of a change.
     */
    void notifyObservers();
}
