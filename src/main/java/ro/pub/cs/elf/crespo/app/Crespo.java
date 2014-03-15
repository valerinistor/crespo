package ro.pub.cs.elf.crespo.app;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Crespo extends JFrame {

	IMediator	mediator;

	public Crespo() {
		super("Crespo");

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Crespo.this.dispose();
				}
			}
		});

		setSize(new Dimension(640, 480));
		setCenter();
		setVisible(true);
	}

	private void setCenter() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) (dimension.getWidth() - this.getWidth()) / 2;
		int y = (int) (dimension.getHeight() - this.getHeight()) / 2;
		setLocation(x, y);
	}

	public static void main(String[] args) {
		new Crespo();
	}
}
