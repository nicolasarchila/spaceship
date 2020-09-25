package com.spaceship.satelite;

public class Satelite {
	
	double x;
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	double y;
	
	public Satelite() {
	}

   public Satelite(double x,double y) {
      this.x = x;
      this.y = y;
   }

}
