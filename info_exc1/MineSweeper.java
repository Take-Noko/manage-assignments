import java.util.Random;

interface MineSweeperGUI {
    public void setTextToTile(int x, int y, String text);

    public void win();

    public void lose();
}

public class MineSweeper {

    private final int height;
    private final int width;
    private final int numberOfTiles;
    private final int numberOfBombs;
    private final int[][] table;
    private final int [][] numberOfAroundMines;
    private int numberOfOpenableTiles;
    private int[] firstLoc = null;


    public MineSweeper(int height, int width, int numberOfBombs) {
        this.height = height;
        this.width = width;
        this.numberOfTiles = height * width;
        this.numberOfBombs = numberOfBombs;
        this.table = new int[height][width];
        this.numberOfOpenableTiles = this. numberOfTiles - this.numberOfBombs;

        // initTable();
        this.numberOfAroundMines = new int[height][width];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    void initTable() {
        setBombs();

        /* ----- ここから実装してください． ----- */
    }

    void setBombs() {
        /* ----- ここから実装してください． ----- */
        if(this.numberOfBombs >= this.numberOfTiles){
            return;
        }
        Random random = new Random();
        int fx = this.firstLoc[0];
        int fy = this.firstLoc[1];
        for(int i = 0; i < this.numberOfBombs; i++){
            int x = random.nextInt(this.width);
            int y = random.nextInt(this.height);

            // ランダムに指定したタイルが地雷なしになるまでパネルを変更
            if(this.numberOfBombs <= this.numberOfTiles - 9){
                /* はじめのパネルの周囲（3x3）以上の安全パネルを確保できる場合 */
                boolean isInRangex = x >= fx - 1 && x <= fx + 1;
                boolean isInRangey = y >= fy - 1 && y <= fy + 1;
                while(this.table[y][x] == -1 || (isInRangex && isInRangey)){
                    x = (x + 1) % this.width;
                    y = (x == 0) ? (y + 1) % this.height : y;
                    isInRangex = x >= fx - 1 && x <= fx + 1;
                    isInRangey = y >= fy - 1 && y <= fy + 1;
                }
            } else {
                while(this.table[y][x] == -1 || (x == fx && y == fy)){
                    x = (x + 1) % this.width;
                    y = (x == 0) ? (y + 1) % this.height : y;
                }
            }

            for(int j = 0; j < 9; j++){
                try{
                    this.numberOfAroundMines[y + j / 3 - 1][x + j % 3 - 1]++;
                } catch(ArrayIndexOutOfBoundsException e){
                    continue;
                }
            }

            this.table[y][x] = -1;
        }
    }

    public void openTile(int x, int y, MineSweeperGUI gui) {
        /* ----- ここから実装してください． ----- */
        if(this.firstLoc == null){
            this.firstLoc = new int[2];
            firstLoc[0] = x;
            firstLoc[1] = y;
            this.initTable();
        }
        int state = this.table[y][x];

        if(state == -1){
            this.openAllTiles(gui);
            gui.lose();
        } else if(state == 0){
            this.chainOpen(x, y, gui);
        }
    }

    private void chainOpen(int x, int y, MineSweeperGUI gui){
        int state;
        try{
            state = this.table[y][x];
        } catch(ArrayIndexOutOfBoundsException e){
            return;
        }
        if(state != 0){
            return;
        }
        this.table[y][x] = 2;
        this.numberOfOpenableTiles--;
        System.out.println(this.numberOfOpenableTiles);
        if(this.numberOfOpenableTiles == 0){
            gui.win();
        }

        gui.setTextToTile(x, y, String.valueOf(this.numberOfAroundMines[y][x]));
        if(this.numberOfAroundMines[y][x] == 0){
            for(int i = 0; i < 9; i++){
                this.chainOpen(x + i % 3 - 1, y + i / 3 - 1, gui);
            }
        }
    }

    public void setFlag(int x, int y, MineSweeperGUI gui) {
        /* ----- ここから実装してください． ----- */
        if(this.firstLoc == null) return;
        int state = this.table[y][x];
        if(state == 0 || state == -1){
            if(state == 0){
                this.table[y][x] = 1;
            } else {
                this.table[y][x] = -2;
            }
            gui.setTextToTile(x, y, "F");
        } else if(state == 1 || state == -2){
            if(state == 1){
                this.table[y][x] = 0;
            } else {
                this.table[y][x] = -1;
            }
            gui.setTextToTile(x, y, "");
        }
    }

    private void openAllTiles(MineSweeperGUI gui) {
        /* ----- ここから実装してください． ----- */
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                int state = this.table[y][x];

                if(state == 2) continue;
                if(state < 0)
                    gui.setTextToTile(x, y, String.valueOf(this.table[y][x]));
                if(state == 0 || state == 1)
                    gui.setTextToTile(x, y, String.valueOf(this.numberOfAroundMines[y][x]));
            }
        }
    }
}
