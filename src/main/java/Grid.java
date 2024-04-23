import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.awt.*;
import java.net.URI;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;

public class Grid {

    private Tile[][] gridArray;

    Grid(){
        this.gridArray = new Tile[12][12];
        for(int i = 0; i<12; i++){

            for(int j = 0; j<12; j++){
                gridArray[i][j] = new Tile(520 + (80 *j),160+(80*i),1);

            }


        }
    }

    public Tile[][] getGridArray(){
        Tile[][] result = this.gridArray;
        return result;
    }




    class Tile{
        private int x, y, direction;

        Tile(int x, int y, int direction){
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public void setDirection(int direction){
            this.direction = direction;
        }

        public int getDirection(){
            return this.direction;

        }

    }
}
