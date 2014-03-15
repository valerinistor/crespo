package ro.pub.cs.elf.crespo.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ro.pub.cs.elf.crespo.gui.Draw;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class Crespo extends JFrame {

	private static final long serialVersionUID = -4702017201445413247L;

	public static int width = 640;
	public static int height = 480;

	private Mediator mediator;
	private final Draw picasso;

	public Crespo() {
		super("Crespo");

		registerExitEvents();
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(width, height));
		this.setCenter();

		this.mediator = new Mediator();
		this.picasso = new Draw(getContentPane(), this.mediator);
		this.picasso.paint();

		this.mediator.registerDraw(picasso);

		this.setVisible(true);

		this.mediator.runWorker();
	}

	private void setCenter() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) (dimension.getWidth() - this.getWidth()) / 2;
		int y = (int) (dimension.getHeight() - this.getHeight()) / 2;
		setLocation(x, y);
	}

	private void registerExitEvents() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Crespo.this.dispose();
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});
	}

	public static void main(String[] args) {
		new Crespo();
	}
}
