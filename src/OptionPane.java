import javax.swing.*; 

public class OptionPane {  
    
    int num;
    OptionPane(boolean status) {  
        if (status == false) {
            JFrame lost = new JFrame();  
            int option = JOptionPane.showConfirmDialog(lost, "Try again?","You lost!!!", JOptionPane.YES_NO_OPTION);
            num = option;
        } else {
            JFrame win = new JFrame();
            int option = JOptionPane.showConfirmDialog(win, "Congratulation, you winn!!!","Congratulation", JOptionPane.YES_NO_OPTION);
            num = option;
        }
        if (num == JOptionPane.YES_OPTION) { 
            GUI gui = new GUI();
            gui.start();
        }
        else {
            System.exit(0);
        }
    } 
}  
