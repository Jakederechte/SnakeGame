import java.io.File;

import javax.swing.JOptionPane;


public class SnakeGame{
	
	public SnakeGame() {
		
		String[] options = {"Easy Mode", "Medium Mode", "Hard Mode", "Highscores"};
        int x = JOptionPane.showOptionDialog(null, "Choose your Difficulty:",
                "Snake - Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        
        if (x == 3) {
        	highscores();
        } else {
        new GameFrame(x);
        }
		
	}
	
	
	public static void main(String[] args) {
		
		new SnakeGame();
	}
	
	public void highscores() {
		
		File file1 = new File(System.getenv("APPDATA") + "/Snake-Highscores.txt");

    	//Checks if file1 exists
    	if(file1.exists() && !file1.isDirectory()){
    		new highscores();
    	}else{
    		JOptionPane.showMessageDialog(null, "There are no Highscores yet", "Snake - Highscores", JOptionPane.ERROR_MESSAGE);
    		new SnakeGame();
    	}
	}
	
}