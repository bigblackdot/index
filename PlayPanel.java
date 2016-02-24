import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;

public class PlayPanel extends JPanel implements KeyListener{
  double base;
  double velocity;
  boolean engaged;
  public PlayPanel(){
    super();
    velocity = 0.02;
  }

  public void keyTyped(KeyEvent e) {}
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 39) {
      engaged = true;
    }
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == 39) {
      engaged = false;
    }
  }

  public void updateTime() {
    base += velocity;
    if( engaged ) {
      if( velocity < 0.10 ) {
        velocity += 0.002;
      }
    }
    else {
      if( velocity > 0.02 ) {
        velocity -= 0.002;        
      }
    }
  }

  public void drawSprocket( Graphics g, int x, int y, double angle, double size, double teethStep ) {
    for( double a = 0; a < Math.PI*2.0; a += teethStep ) {
      double cos1  = Math.cos( a+angle );
      double sin1  = Math.sin( a+angle );
      double cos2  = Math.cos( a+angle + teethStep*0.5 );
      double sin2  = Math.sin( a+angle + teethStep*0.5 );
      double cos3  = Math.cos( a+angle + teethStep );
      double sin3  = Math.sin( a+angle + teethStep );

      double x1  = cos1 * size;
      double y1  = sin1 * size;
      double x2  = cos2 * size*0.8;
      double y2  = sin2 * size*0.8;
      double x3  = cos3 * size;
      double y3  = sin3 * size;
      g.drawLine( x + (int)x1, y + (int)y1, x + (int)x2, y + (int)y2 );  
      g.drawLine( x + (int)x2, y + (int)y2, x + (int)x3, y + (int)y3 );  
    }

  }
  public void paintComponent(Graphics g) {
    updateTime();
    drawSprocket( g, 150, 150, base, 80.0, Math.PI / 12 );
    drawSprocket( g, 250, 105, (0-base)*2, 40.0, Math.PI / 8 );
  }

  public static void main(String arg[]){
    JFrame frame = new JFrame("PlayPanel");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(320,320);

    PlayPanel panel = new PlayPanel();
    frame.addKeyListener( panel );

    frame.setContentPane(panel);
    frame.setVisible(true);

    ActionListener taskPerformer = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        frame.repaint();
      }
    };

    new Timer( 1000/60, taskPerformer).start();
  }
}