package ro.pub.cs.elf.crespo.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.swing.JFrame;

import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.dto.UserFile;
import ro.pub.cs.elf.crespo.gui.Draw;
import ro.pub.cs.elf.crespo.mediator.Mediator;

/**
 * Main window class
 * 
 */
public class Crespo extends JFrame {

	private static final long serialVersionUID = -4702017201445413247L;

	public static int width = 640; // window width
	public static int height = 480; // window height

	private final Mediator mediator;
	private final Draw picasso;

	public Crespo(User user) {
		super("Crespo");

		registerExitEvents();
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(width, height));
		this.setCenter();

		this.mediator = new Mediator();

		this.picasso = new Draw(getContentPane(), this.mediator);
		this.picasso.paint();

		// register gui client to mediator
		this.mediator.registerDraw(picasso);
		// register self user
		this.mediator.registerMe(user);

		this.setVisible(true);

		// run swing workers
		this.mediator.runWorkers();
	}

	/**
	 * Set main window to screen center
	 */
	private void setCenter() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) (dimension.getWidth() - this.getWidth()) / 2;
		int y = (int) (dimension.getHeight() - this.getHeight()) / 2;
		setLocation(x, y);
	}

	/**
	 * register exit bindings
	 */
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

	/**
	 * load logged user from property file
	 * 
	 * @return
	 */
	public static User loadUser(String me) {
		Properties userProp = new Properties();
		try {
			userProp.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(me + ".properties"));
		} catch (IOException e) {
			System.err.println("Unable to load user property file");
		}

		User user = new User(userProp.getProperty("user.name"));
		try {
			Inet4Address ipAddress = (Inet4Address) Inet4Address
					.getByName(userProp.getProperty("user.ip"));
			user.setIpAddress(ipAddress);
		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
		}
		user.setPort(Integer.parseInt(userProp.getProperty("user.port")));

		File userHome = new File(userProp.getProperty("user.home"));
		for (File file : userHome.listFiles()) {
			user.addSharedFile(new UserFile(user, file.getPath()));
		}
		return user;
	}

	/**
	 * main run method
	 * 
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		new Crespo(loadUser(args[0]));
	}
}
