package model;

import java.util.ArrayList;

public interface IPlayer {
	
	public void accelerate();
	public void jump();
	public int getBoostsLeft();
	public void startBoost();
	public void boost();
	public boolean getAlive();
	public ArrayList<Shape> getPath();
	public void crash(Intersection i);
}
