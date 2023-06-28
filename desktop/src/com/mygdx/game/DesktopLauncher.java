package com.mygdx.game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Broker broker = new Broker(8080);
		broker.run();
	}
}