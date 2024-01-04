

package assignment1;


import java.io.File;

import java.util.ArrayList;
import java.util.List;

import assignment1.Observable;
import assignment1.Observer;

public class AudioControl extends Observable {
	
	private List<Observer> moves;
	private String newWavFilePath;
	
	public AudioControl() {
		this.moves = new ArrayList<>();
		this.setObservableState(false);
	}

	@Override
	public void register(Observer o) {
		// TODO Auto-generated method stub
		if (this.moves.indexOf(o)	== -1)
			this.moves.add(o);
	}

	@Override
	public void unregister(Observer o) {
		// TODO Auto-generated method stub
		int pos = this.moves.indexOf(o);
		if (pos >= 0)
			this.moves.remove(pos);
	}

	@Override
	public void notifyObserver() {
		// TODO Auto-generated method stub
		if (this.getObservableState()) {
		for (Observer ob: this.moves)
			ob.update();
		this.setObservableState(false);
		}
	}
	
	public void setNewFile(String newWavFilePath) {
		this.newWavFilePath = newWavFilePath;
		this.setObservableState(true);
	};
	
	public String getNewFilePath() {
		return this.newWavFilePath;
	}

}
