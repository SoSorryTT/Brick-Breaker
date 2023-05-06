import javax.swing.*;  
public class OptionPaneLost {  
    JFrame lost;  
    OptionPaneLost() {  
        lost = new JFrame();  
        int a = JOptionPane.showConfirmDialog(lost, "Try again?","You lost!!!", JOptionPane.YES_NO_OPTION);
        if (a == JOptionPane.YES_OPTION) {  
            GUI gui = new GUI();
            gui.start();
        }
        else {
            System.exit(0);
        }
    } 
}  
