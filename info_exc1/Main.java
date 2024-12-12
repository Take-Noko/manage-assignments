import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;


public class Main extends Frame implements WindowListener, MineSweeperGUI {

    private MineSweeper ms;
    private Button[][] tileTable;
    private static final Font f = new Font("serif", Font.BOLD, 16);
    private final ResultDialog resultDialog = new ResultDialog(this, "Result");

    public Main() {
        super("MineSweeper");
        ms = new MineSweeper(25, 25, 150);  // 地雷が10個ある9×9の盤面
        init();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void init() {
        this.tileTable = new Button[ms.getHeight()][ms.getWidth()];
        this.addWindowListener(this);
        this.setLayout(new GridLayout(ms.getHeight(), ms.getWidth()));
        for (int i = 0; i < ms.getHeight(); i++) {
            for (int j = 0; j < ms.getWidth(); j++) {
                Button tile = new Button();
                tile.setBackground(Color.LIGHT_GRAY);
                tile.setFont(f);
                tile.addMouseListener(new MouseEventHandler(ms, this, j, i));
                tileTable[i][j] = tile;
                this.add(tile);
            }
        }
        this.setSize(50 * ms.getWidth(), 50 * ms.getHeight());
        this.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void setTextToTile(int x, int y, String text) {
        Button tile = tileTable[y][x];
        switch(text){
            case "":
                break;
            case "F":
                text = "F";
                tile.setForeground(Color.blue);
                break;
            case "-2":
            case "-1":
                text = "@";
                tile.setForeground(Color.white);
                tile.setBackground(Color.black);
                break;
            case "0":
                text = "";
                tile.setBackground(new Color(170, 170, 170));
                break;
            case "1":
                tile.setForeground(Color.black);
                tile.setBackground(new Color(170, 170, 170));
                break;
            case "2":
                tile.setForeground(Color.cyan);
                tile.setBackground(new Color(170, 170, 170));
                break;
            case "3":
                tile.setForeground(Color.green);
                tile.setBackground(new Color(170, 170, 170));
                break;
            case "4":
                tile.setForeground(Color.red);
                tile.setBackground(new Color(170, 170, 170));
                break;
            case "5":
                tile.setForeground(Color.pink);
                tile.setBackground(new Color(170, 170, 170));
                break;
            case "6":
                tile.setForeground(Color.yellow);
                tile.setBackground(new Color(170, 170, 170));
                break;
            case "7":
                tile.setForeground(Color.magenta);
                tile.setBackground(new Color(170, 170, 170));
                break;
            case "8":
                tile.setForeground(Color.orange);
                tile.setBackground(new Color(170, 170, 170));
                break;
        }

        this.tileTable[y][x].setLabel(text);
    }

    @Override
    public void win() {
        resultDialog.showDialog("Win !!!");
    }

    @Override
    public void lose() {
        resultDialog.showDialog("Lose ...");
    }
}


class MouseEventHandler implements MouseListener {

    MineSweeper ms;
    MineSweeperGUI msgui;
    int x, y;

    MouseEventHandler(MineSweeper ms, MineSweeperGUI msgui, int x, int y) {
        this.ms = ms;
        this.msgui = msgui;
        this.x = x;
        this.y = y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1: {
                // Left click
                ms.openTile(x, y, msgui);
            }
            break;
            case MouseEvent.BUTTON2: {
                // Wheel click
            }
            break;
            case MouseEvent.BUTTON3: {
                // Right click
                ms.setFlag(x, y, msgui);
            }
            break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}


class ResultDialog extends Dialog {

    Label label;
    Button btn;

    public ResultDialog(Frame owner, String title) {
        super(owner, title);
        setLayout(new GridLayout(2, 1));
        Panel p1 = new Panel();
        label = new Label();
        p1.add(label);
        this.add(p1);
        this.setSize(200, 100);
        btn = new Button();
        btn.setLabel("exit");
        btn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        Panel p2 = new Panel();
        p2.add(btn);
        this.add(p2);
    }

    public void showDialog(String message) {
        Panel p1 = new Panel();
        this.label.setText(message);
        this.setVisible(true);
    }
}
