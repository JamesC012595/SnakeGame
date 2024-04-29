import com.raylib.Raylib;
import org.java_websocket.drafts.Draft_6455;

import java.net.URI;
import java.net.URISyntaxException;

import static com.raylib.Jaylib.*;
import static com.raylib.Jaylib.RAYWHITE;

class Game {
    private InputClient inputClient;
    private Player player;
    private Direction directionShift;
    private Grid grid;
    private Apple granny;
    private boolean isRunning;



    public Game(int width, int height, boolean fullscreen) throws URISyntaxException {
        InitWindow(width, height, "Snake");
        SetTargetFPS(200);
        if (fullscreen) ToggleFullscreen();
        player = new Player();
        directionShift = Direction.RIGHT;
        int[] xYArray = {1,1};
        grid = new Grid();
        spawnApple();

        URI uri = new URI("ws://192.168.4.1:80/ws");
        inputClient = new InputClient(new URI("ws://192.168.4.1:80/ws"), new Draft_6455());
        inputClient.connect();
    }

    public void run() {

        isRunning = true;

        while (!WindowShouldClose()) {

            if (!isRunning) {
                BeginDrawing();
                DrawText(("End Score: "+(player.getSize() -2)/2),700,550,40, GREEN);
                EndDrawing();
                continue;
            }

            if(IsKeyPressed(KEY_H)) {
                break;
            }

            update();
            draw();


        }

        inputClient.close();
        CloseWindow();
    }

    private void update() {
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
            directionShift = Direction.RIGHT;
        }
        if(IsKeyPressed(KEY_DOWN)){
            directionShift = Direction.DOWN;
        }
        if(IsKeyPressed(KEY_LEFT)){
            directionShift = Direction.LEFT;
        }

        if (player.checkCollision()) {
            isRunning = false;
        }

        if (player.checkCollision(granny)) {
            player.extendSnake();
            spawnApple();
        }

        player.update(directionShift, grid);
    }

    private void draw() {
        BeginDrawing();
        {
            ClearBackground(rgbaToColor(192, 202, 245, 100));

            DrawBoard();

            DrawText("Score: " + ((player.getSize() - 2) / 2), 20, 20, 50, GREEN);
            DrawGrid(20, 1.0f);
            DrawFPS(20, 1160);


            player.draw();
            DrawCircle(this.granny.x, this.granny.y, 30, RED);
        }
        EndDrawing();
    }

    private void DrawBoard(){

        Raylib.Color lightBlue = rgbaToColor(0,150,255,160);
        Raylib.Color lightPurple = rgbaToColor(110,30,255,110);
        Raylib.Color lighterPurple = rgbaToColor(210,0,255,60);
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

    private void spawnApple(){
        int[] location = player.getNextAppleLocation();
        this.granny = new Apple(location[0], location[1]);
    }

    private static Raylib.Color rgbaToColor(int var0, int var1, int var2, int var3) {
        return (new Raylib.Color()).r((byte)var0).g((byte)var1).b((byte)var2).a((byte)var3);
    }

}
