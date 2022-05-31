import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class highscores {

	private static int SCREEN_WIDTH = 600;
	private static int SCREEN_HEIGHT = SCREEN_WIDTH;
	private int mode = 1;
	private JButton easy;
	private JButton medium;
	private JButton hard;
	private JFrame frame;
	
	private int[] scores = new int[] {0, 0, 0};
	private String[] names = new String[] {"", "", ""};
	
    public highscores() {
    	
    	
    	
        frame = new JFrame("Snake - Highscores") {
        	
            public void paint(Graphics g) {
            	
            	g.clearRect(0, 0, SCREEN_WIDTH,	SCREEN_HEIGHT);
            	
            	//Überschrift
            	g.setColor(Color.red);
    			g.setFont(new Font ("Impact", Font.BOLD, 60));
    			FontMetrics metrics = getFontMetrics(g.getFont());
    			g.drawString("Highscores", (SCREEN_WIDTH - metrics.stringWidth("Highscores")) / 2, 100);
    			g.drawLine((SCREEN_WIDTH - metrics.stringWidth("Highscores")) / 2, 110, SCREEN_WIDTH - ((SCREEN_WIDTH - metrics.stringWidth("Highscores")) / 2), 110);
    			
    			g.setFont(new Font ("Impact", Font.BOLD, 40));
    			if (scores[0] > 0) {
    				
    				g.drawString("1:  " + names[0], 150, 250);
    				g.drawString(Integer.toString(scores[0]), 400, 250);
    				
    	    	}if (scores[1] > 0) {
    	    		
    	    		g.drawString("2:  " + names[1], 150, 300);
    				g.drawString(Integer.toString(scores[1]), 400, 300);
    	    		
    	    	}if (scores[2] > 0) {
    	    		
    	    		g.drawString("3:  " + names[2], 150, 350);
    				g.drawString(Integer.toString(scores[2]), 400, 350);
    	    		
    	    	}
            }
        };
        easy = new JButton("Easy");
        medium = new JButton("Medium");
        hard = new JButton("Hard");
        
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setBackground(Color.black);
		frame.requestFocus();
		
		easy.setBounds(160, 100, 70, 50);
		easy.setBackground(Color.green);
		easy.setFocusPainted(false);
		easy.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		easy.setVisible(true);
		frame.add(easy);
		easy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent wahrf) {
				if (mode != 1) {
					mode = 1;
					mode();
				}
			}
		});
		
		medium.setBounds(265, 100, 70, 50);
		medium.setBackground(Color.green);
		medium.setFocusPainted(false);
		medium.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		medium.setVisible(true);
		frame.add(medium);
		medium.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent wahrf) {
				if (mode != 2) {
					mode = 2;
					mode();
				}
			}
		});
		
		hard.setBounds(370, 100, 70, 50);
		hard.setBackground(Color.green);
		hard.setFocusPainted(false);
		hard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		hard.setVisible(true);
		frame.add(hard);
		hard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent wahrf) {
				if (mode != 3) {
					mode = 3;
					mode();
				}
			}
		});
		
		frame.setVisible(true);
		mode();
    }
    
    
    public void mode() {
    	easy.setBackground(Color.red);
    	medium.setBackground(Color.red);
    	hard.setBackground(Color.red);
    	
    	scores[0] = 0;
    	scores[1] = 0;
    	scores[2] = 0;
    	
    	if (mode == 1) {
    		easy.setBackground(Color.green);
    		highscore("easy");
    		frame.repaint();
    		
    	}else if (mode == 2) {
    		medium.setBackground(Color.green);
    		highscore("medium");
    		frame.repaint();
    		
    	}else if (mode == 3) {
    		hard.setBackground(Color.green);
    		highscore("hard");
    		frame.repaint();
    		
    	}
    }
    
    public void highscore(String mode) {
    	
    	try {
	         String Meldung;
	         Meldung = mode;
	 
	         String aktline = "";
	         BufferedReader inFile = new BufferedReader (new FileReader (System.getenv("APPDATA") + "/Snake-Highscores.txt"));
	         aktline = inFile.readLine();
	 
	         while (aktline!= null)
	         {
	            if (aktline.startsWith (Meldung))
	            {
	            	
	        		String[] splitArray = aktline.split("(\\s|\\p{Punct})+");
	        		
	        		for (int i = 0; i < splitArray.length; i++){
	        			
	        			if(splitArray[i].contains("score")){
	        				
	        				if (Integer.parseInt(splitArray[i + 1]) > scores[0]) {
	        					scores[2] = scores[1];
	        					names[2] = names[1];
	        					scores[1] = scores[0];
	        					names[1] = names[0];
	        					
	        					scores[0] = Integer.parseInt(splitArray[i + 1]);
	        					names[0] = splitArray[i + 3];	
	        					
	        				}else if (Integer.parseInt(splitArray[i + 1]) > scores[1]) {
	        					scores[1] = scores[0];
	        					names[1] = names[0];
	        					
	        					scores[1] = Integer.parseInt(splitArray[i + 1]);
	        					names[1] = splitArray[i + 3];
	        					
	        				}else if (Integer.parseInt(splitArray[i + 1]) > scores[2]) {
	        					scores[2] = Integer.parseInt(splitArray[i + 1]);
	        					names[2] = splitArray[i + 3];
	        				}
	        				
	        			}
	        			  
	        			  
	        		}
	            
	            }
	            aktline = inFile.readLine();   
	         }
	         inFile.close();
	    }
	 
	        catch(Exception ex)
	         {
	        	
	 
	       }
    }
    
    
}










































