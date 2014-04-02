package graphic_system;

import graphic_system.controller.GraphicSystem;
import graphic_system.view.Layout;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Layout gui = new Layout();
				GraphicSystem graphicSystem = new GraphicSystem(gui);
			}
		});
	}
}
