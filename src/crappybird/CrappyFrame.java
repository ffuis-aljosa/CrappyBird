package crappybird;

import javax.swing.JFrame;

public class CrappyFrame extends JFrame {

    public CrappyFrame() {
        CrappyPanel panel = new CrappyPanel();
        
        add(panel);
        pack();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
}
