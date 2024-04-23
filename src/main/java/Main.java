import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.awt.*;
import java.net.URI;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;

public class Main {
    public static void main(String args[]) {

        InitWindow(1920, 1200, "Demo");
        SetTargetFPS(80);
        ToggleFullscreen();
        Player play = new Player();
        int directionShift=1;
        int[] xYArray = {1,1};
        Grid grid = play.getGrid();
        //InputClient inputClient = new InputClient(new URI("WS://(ip of ESP)"))

        while (!WindowShouldClose()) {

            if(IsKeyPressed(KEY_H))
                break;
            DrawText("Score: " + ((play.getSize()-2)/2),20,20,50,GREEN);
            ClearBackground(c(192,202,245,100));
            DrawGrid(20, 1.0f);
            DrawFPS(20, 1160);

            DrawBoard();

            DrawCircle(100,180,40,GREEN);



            if(IsKeyPressed(KEY_UP)){
                directionShift =0;
            }
            if(IsKeyPressed(KEY_RIGHT)){
                directionShift =1;
            }
            if(IsKeyPressed(KEY_DOWN)){
                directionShift =2;
            }
            if(IsKeyPressed(KEY_LEFT)){
                directionShift =3;
            }
            play.moveSnake(8);

            xYArray = play.getHeadXY();
            if((xYArray[0]+40)%80==0&&xYArray[1]%80==0){
                play.setDirection1(directionShift);
            }
            if((xYArray[0])%80==0||(xYArray[1]+40)%80==0){
                play.setDirection2();
                play.extendSnake();
            }
            play.DrawPlayer();
            EndDrawing();
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


        DrawRectangle(20,100,80,80, lightBlue);
        DrawRectangle(100,100,80,80,lighterPurple);
        DrawRectangle(20,180,80,80,BLUE);
        DrawRectangle(100,180,80,80,lightPurple);
    }


    private static Raylib.Color c(int var0, int var1, int var2, int var3) {
        return (new Raylib.Color()).r((byte)var0).g((byte)var1).b((byte)var2).a((byte)var3);
    }
}