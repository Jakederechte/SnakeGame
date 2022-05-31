import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame{
	
	private GamePanel gg;
	private static JButton button;
	private static int score;
	private boolean exist;
	private static String mode;
	
	public GameFrame(int x) {
		
		if (x != 10) {
		this.add(gg = new GamePanel(x));
		this.setTitle("Snake - Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		exist = false;
		score = 0;
		
		button = new JButton ("Try Again");
		button.setFocusable(false);
		gg.add(button);
		button.setVisible(false);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = new Date();
				
			    // prompt the user to enter their name
			    String name = JOptionPane.showInputDialog("Enter Your Name to safe score (" + score + ")");
				
			    if (name == null) {
			    	System.exit(0);
			    } else {
			    	
			    	File file = new File(System.getenv("APPDATA") + "/Snake-Highscores.txt");
			        
			        if(file.exists() && !file.isDirectory()){
			    		exist = true;
			    	}
			        
			            try
			            {    
			                String filePath = System.getenv("APPDATA") + "/Snake-Highscores.txt";
			                FileOutputStream f = new FileOutputStream(filePath, true);
			                
			                if(!exist){
			                	String lineToAppend = "a complete list of all highscores set and saved so far:\r\n";    
			                	byte[] byteArr = lineToAppend.getBytes(); //converting string into byte array
			                	f.write(byteArr);
			                }
			                String lineToAppend2 = "\r\n" + mode + " score " + score + " name " + name + " date (" + date + ")";
		                	byte[] byteArr2 = lineToAppend2.getBytes(); //converting string into byte array
		                	f.write(byteArr2);
		                	f.close();
			                
			            }
			            catch(Exception f)
			            {
			                System.out.println(f);
			            }
				
			    }
				again();
			}
		});
		
		}
	}
	
	public void again() {
		this.dispose();
		new SnakeGame();
	}
	
	public static void ButtonVisible(int scorenew, int diff) {
		score = scorenew;
		if (diff == 0) {
			mode = "easy";
		}else if (diff == 1) {
			mode = "medium";
		}else if (diff == 2) {
			mode = "hard";
		}
		
		button.setVisible(true);
	}

}
