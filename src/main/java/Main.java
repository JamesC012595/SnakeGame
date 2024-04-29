import java.net.URISyntaxException;

public class Main {
    public static void main(String args[]) {
        try {
            Game game = new Game(1920, 1080, false);
            game.run();
        } catch (URISyntaxException e) {
            System.err.println(e.getMessage());
        }
    }

}