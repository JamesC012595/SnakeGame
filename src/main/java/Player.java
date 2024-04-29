import static com.raylib.Jaylib.*;

import java.util.Random;


public class Player { 
    private int size;
    private Node head;
    private Node tail;


    Player(){
        this.head = new Node();
        this.tail = new Node(this.head,this.head.x -40,this.head.y ,1);
        this.size =2;
    }

    public void draw(){
        Node temp = this.head;
        DrawCircle(temp.x, temp.y, 30, GREEN);
        switch (temp.direction) {
            case 0 -> DrawCircle(temp.x - 5, temp.y - 20, 5, BLACK);
            case 1 -> DrawCircle(temp.x + 20, temp.y - 5, 5, BLACK);
            case 2 -> DrawCircle(temp.x + 5, temp.y + 20, 5, BLACK);
            case 3 -> DrawCircle(temp.x - 20, temp.y - 5, 5, BLACK);
        }
        temp = temp.next;
        while (temp != null) {
            DrawCircle(temp.x, temp.y, 30, GREEN);
            temp = temp.next;
        }
    }

    public void update(Direction direction, Grid grid) {
        moveSnake(2);
        if ((head.x + 40) % 80 == 0 && head.y % 80 == 0) {
            setDirection(direction.ordinal(), grid);
        }
        if ((head.x) % 80 == 0 || (head.y + 40) % 80 == 0) {
            setDirection(grid);
        }
    }

    public void setDirection(int direction, Grid grid){
        Node temp = this.head;
        this.head.direction = direction;
        if(((temp.y-160)/80)>-1&&((temp.y-160)/80)<12&&((temp.x-520)/80)>-1&&((temp.x-520)/80)<12)
            grid.getGridArray()[((this.head.y-160)/80)][((this.head.x-520)/80)].setDirection(this.head.direction);
        temp = temp.next;
        while(temp!=null){
            if(((temp.x+40)%80==0)&&(temp.y%80==0)){
                if(((temp.y-160)/80)>-1&&((temp.y-160)/80)<12&&((temp.x-520)/80)>-1&&((temp.x-520)/80)<12)
                    temp.direction = grid.getGridArray()[((temp.y-160)/80)][((temp.x-520)/80)].getDirection();
            }
            temp = temp.next;
        }
    }

    public void setDirection(Grid grid){
        Node temp = this.head;
        while(temp!=null){
            if(((temp.x+40)%80==0)&&(temp.y%80==0)){
                    if(((temp.y-160)/80)>-1&&((temp.y-160)/80)<12&&((temp.x-520)/80)>-1&&((temp.x-520)/80)<12)
                        temp.direction = grid.getGridArray()[((temp.y-160)/80)][((temp.x-520)/80)].getDirection();
            }
            temp = temp.next;
        }
    }




    public void extendSnake() {
        switch (this.tail.direction) {
            case 0 -> {
                this.tail.next = new Node(this.tail, this.tail.x, this.tail.y + 40, 0);
                this.tail = this.tail.next;
                this.tail.next = new Node(this.tail, this.tail.x, this.tail.y + 40, 0);
                this.tail = this.tail.next;
            }
            case 1 -> {
                this.tail.next = new Node(this.tail, this.tail.x - 40, this.tail.y, 1);
                this.tail = this.tail.next;
                this.tail.next = new Node(this.tail, this.tail.x - 40, this.tail.y, 1);
                this.tail = this.tail.next;
            }
            case 2 -> {
                this.tail.next = new Node(this.tail, this.tail.x, this.tail.y - 40, 2);
                this.tail = this.tail.next;
                this.tail.next = new Node(this.tail, this.tail.x, this.tail.y - 40, 2);
                this.tail = this.tail.next;
            }
            case 3 -> {
                this.tail.next = new Node(this.tail, this.tail.x + 40, this.tail.y, 3);
                this.tail = this.tail.next;
                this.tail.next = new Node(this.tail, this.tail.x + 40, this.tail.y, 3);
                this.tail = this.tail.next;
            }
        }
        this.size+=2;
    }

    public void moveSnake(int speed){
        Node temp = this.head;
        while(temp!=null){
            switch (temp.direction) {
                case 0 -> temp.y = temp.y - speed;
                case 1 -> temp.x = temp.x + speed;
                case 2 -> temp.y = temp.y + speed;
                case 3 -> temp.x = temp.x - speed;
            }
            temp = temp.next;

        }
    }

    public int getSize(){
        return this.size;
    }

    public boolean checkCollision(Apple apple) {
        Node temp = this.head;
        while (temp != null) {
            if (temp.x == apple.x && temp.y == apple.y) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public boolean checkCollision() {
        if(this.head.x>1440||this.head.x<480||this.head.y>1080||this.head.y<120) {
            return true;
        }
        Node temp = this.head.next;
        for(int i = 1; i < this.size; i++){
            if(this.head.x == temp.x && this.head.y == temp.y){
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public int[] getNextAppleLocation() {
        boolean repeat = true, check = false;
        int x = 0, y = 0;
        Random rand = new Random();
        Player.Node temp = this.head;
        while(repeat) {
            check = true;
            x = 520 + 80* rand.nextInt(11);
            y = 160 + 80* rand.nextInt(11);
            temp = this.head;
            for(int k = 0;k<this.size;k++){
                if(x==temp.x&&y==temp.y){
                    check = false;
                }
                temp = temp.next;
            }
            if(check)
                repeat = false;

        }
        return new int[]{x, y};
    }


    private class Node {
        private Boolean eyes;
        private int x,y;
        private Node next;
        private int direction;

        Node(){
            this.eyes = true;
            this.x = 600;
            this.y = 560;
            direction = 1;
        }
        Node(Node prev,int x,int y, int direction){
            prev.next = this;
            this.eyes = false;
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }



}
