package assignment1;

public abstract class Observable {

	private boolean observableState;

	public abstract void register(Observer o);
	public abstract void unregister(Observer o);
	public abstract void notifyObserver();

	public void setObservableState(boolean observableState) {
		this.observableState = observableState;
		if (this.observableState)
			this.notifyObserver();
	}

	public boolean getObservableState() {
		return this.observableState;
	}
}
