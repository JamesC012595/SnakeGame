import com.raylib.Jaylib;
import com.raylib.Raylib;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;

enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT
}

public class Main {
    public static void main(String args[]) {


        InitWindow(1920, 1200, "Snake");
        SetTargetFPS(200);
        ToggleFullscreen();
        Player play = new Player();
        Direction directionShift= Direction.RIGHT;
        int[] xYArray = {1,1};
        Grid grid = play.getGrid();
        try {
            URI uri = new URI("ws://192.168.4.1:80/ws");
            InputClient inputClient = new InputClient(new URI("ws://192.168.4.1:80/ws"), new Draft_6455());
            inputClient.connect();

            while (!WindowShouldClose()) {

                if(IsKeyPressed(KEY_H))
                    break;
                DrawText("Score: " + ((play.getSize()-2)/2),20,20,50,GREEN);
                ClearBackground(c(192,202,245,100));
                DrawGrid(20, 1.0f);
                DrawFPS(20, 1160);

                DrawBoard();

                //DrawCircle(100,180,40,GREEN);

                if(inputClient.inputs.get(1)==2){
                    directionShift = Direction.UP;
                }
                if(inputClient.inputs.get(1)==1){
                    directionShift = Direction.DOWN;
                }
                if(inputClient.inputs.get(0)==2){
                    directionShift = Direction.RIGHT;
                }
                if(inputClient.inputs.get(0)==1){
                    directionShift = Direction.LEFT;
                }


                if(IsKeyPressed(KEY_UP)){
                    directionShift = Direction.UP;
                }
                if(IsKeyPressed(KEY_RIGHT)){
                    directionShift =Direction.RIGHT;
                }
                if(IsKeyPressed(KEY_DOWN)){
                    directionShift =Direction.DOWN;
                }
                if(IsKeyPressed(KEY_LEFT)){
                    directionShift =Direction.LEFT;
                }


                play.moveSnake(2);
                if(play.getGameRunning()) {
                    xYArray = play.getHeadXY();
                    if ((xYArray[0] + 40) % 80 == 0 && xYArray[1] % 80 == 0) {
                        play.checkCollision();
                        play.setDirection1(directionShift.ordinal());
                    }
                    if ((xYArray[0]) % 80 == 0 || (xYArray[1] + 40) % 80 == 0) {
                        play.checkCollision();
                        play.setDirection2();
                        //play.extendSnake();
                    }
                }
                play.DrawPlayer();
                EndDrawing();
            }

            inputClient.close();
        }catch(URISyntaxException use){
            System.out.println(use.getMessage());
        }
        CloseWindow();

    }

    public static void DrawBoard(){

        Raylib.Color lightBlue = c(0,150,255,160);
        Raylib.Color lightPurple = c(110,30,255,110);
        Raylib.Color lighterPurple = c(210,0,255,60);
        DrawRectangle(455,95,1010,1010,BLUE);
        DrawRectangle(480,120,960,960,RAYWHITE);
        DrawRectangle(480,120,960,960, lightBlue);

        for(int i = 0; i<12; i++){
            if((i+1)%2==0){
                for(int j = 0; j<6; j++){
                    DrawRectangle(480 + (160 *j),120+(80*i),80,80,lighterPurple);
                }
            } else {
                for(int j = 1; j<=6; j++){
                    DrawRectangle(400 + (160 *j),120+(80*i),80,80,lighterPurple);
                }
            }
        }

        /*
        DrawRectangle(20,100,80,80, lightBlue);
        DrawRectangle(100,100,80,80,lighterPurple);
        DrawRectangle(20,180,80,80,BLUE);
        DrawRectangle(100,180,80,80,lightPurple);

         */
    }


    private static Raylib.Color c(int var0, int var1, int var2, int var3) {
        return (new Raylib.Color()).r((byte)var0).g((byte)var1).b((byte)var2).a((byte)var3);
    }
}