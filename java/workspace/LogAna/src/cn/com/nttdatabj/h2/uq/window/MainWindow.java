package cn.com.nttdatabj.h2.uq.window;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import cn.com.nttdatabj.h2.uq.window.menu.file.MenuFile;
import cn.com.nttdatabj.h2.uq.window.menu.tools.MenuTools;

public class MainWindow {

	private JFrame frame;
	private static MainWindow _instance = new MainWindow();

	public static MainWindow getInstance() {
		return _instance;
	}
	
	public void setTitle(String winTitle) {
		frame.setTitle("Log解析".concat(" - ").concat(winTitle));
	}
	
	/**
	 * Create the application.
	 */
	private MainWindow() {
		super();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createWindow();
		setIcon();
		setMenu();
		setVisible(true);
	}
	
	/**
	 * Create the window
	 */
	private void createWindow() {
		frame = new JFrame();
		frame.setTitle("log解析器");
		frame.setBounds(100,100,960,640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	/**
	 * Set menubar for the window
	 */
    private void setMenu() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		final MenuFile mnFile = new MenuFile();
		menuBar.add(mnFile.CreateMenu());
		
		final MenuTools mnTools = new MenuTools();
		menuBar.add(mnTools.CreateMenu());
    }
    
    /**
     * 
     */
    private void setVisible(boolean visible) {
		frame.setVisible(visible);	
    }
    
    private void setIcon() {
        ImageIcon icon = new ImageIcon("./img/logo.png");
        frame.setIconImage(icon.getImage());
    }
}
