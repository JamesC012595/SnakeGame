import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;


public class Player { 
    private int size;
    private Node head;
    private Node tail;
    private Grid grid;

    Player(){
        this.head = new Node();
        this.tail = new Node(this.head,this.head.x -40,this.head.y ,1);
        /*
        this.tail.next = new Node(this.tail,this.head.x-80,this.head.y,1);
        this.tail = this.tail.next;
        this.tail.next = new Node(this.tail,this.head.x-120,this.head.y,1);
        this.tail = this.tail.next;
         */
        this.size =2;
        this.grid = new Grid();

    }

    public void DrawPlayer(){
        Node temp = this.head;
        DrawCircle(temp.x, temp.y, 30,GREEN);
        DrawCircle(temp.x+20,temp.y-5,5,BLACK);
        temp=temp.next;
        while(temp!=null){
            DrawCircle(temp.x,temp.y,30,GREEN);
            temp = temp.next;
        }
    }

    public int[] getHeadXY(){
        int[] result = {this.head.x,this.head.y};
        return result;
    }

    public void setDirection1(int direction){
        Node temp = this.head;
        this.head.direction = direction;
        if(((temp.y-160)/80)>-1&&((temp.y-160)/80)<12&&((temp.x-560)/80)>-1&&((temp.x-560)/80)<12)
            this.grid.getGridArray()[((this.head.y-160)/80)][((this.head.x-560)/80)].setDirection(this.head.direction);
        temp = temp.next;
        while(temp!=null){
            if(((temp.x+40)%80==0)&&(temp.y%80==0)){
                if(((temp.y-160)/80)>-1&&((temp.y-160)/80)<12&&((temp.x-560)/80)>-1&&((temp.x-560)/80)<12)
                    temp.direction = this.grid.getGridArray()[((temp.y-160)/80)][((temp.x-560)/80)].getDirection();
            }
            temp = temp.next;
        }
    }

    public void setDirection2(){
        Node temp = this.head;
        while(temp!=null){
            if(((temp.x+40)%80==0)&&(temp.y%80==0)){
                    if(((temp.y-160)/80)>-1&&((temp.y-160)/80)<12&&((temp.x-560)/80)>-1&&((temp.x-560)/80)<12)
                        temp.direction = this.grid.getGridArray()[((temp.y-160)/80)][((temp.x-560)/80)].getDirection();
            }
            temp = temp.next;
        }
    }

    public Grid getGrid(){
        return this.grid;
    }

    public void extendSnake() {
        switch (this.tail.direction) {
            case 0:
                this.tail.next = new Node(this.tail, this.tail.x, this.tail.y+40, 0);
                this.tail = this.tail.next;
                this.tail.next = new Node(this.tail, this.tail.x, this.tail.y+80, 0);
                this.tail = this.tail.next;
                break;
            case 1:
            this.tail.next = new Node(this.tail, this.tail.x - 40, this.tail.y, 1);
            this.tail = this.tail.next;
            this.tail.next = new Node(this.tail, this.tail.x - 80, this.tail.y, 1);
            this.tail = this.tail.next;
            break;
            case 2:
                this.tail.next = new Node(this.tail, this.tail.x, this.tail.y-40, 2);
                this.tail = this.tail.next;
                this.tail.next = new Node(this.tail, this.tail.x, this.tail.y-80, 2);
                this.tail = this.tail.next;
                break;
            case 3:
                this.tail.next = new Node(this.tail, this.tail.x + 40, this.tail.y, 3);
                this.tail = this.tail.next;
                this.tail.next = new Node(this.tail, this.tail.x + 80, this.tail.y, 3);
                this.tail = this.tail.next;
                break;
        }
        this.size+=2;
    }

    public void moveSnake(int speed){
        Node temp = this.head;
        while(temp!=null){
            switch(temp.direction){
                case 0:
                    temp.y = temp.y -speed;
                    break;
                case 1:
                    temp.x = temp.x +speed;
                    break;
                case 2:
                    temp.y = temp.y +speed;
                    break;
                case 3:
                    temp.x = temp.x -speed;
                    break;
            }
            temp = temp.next;

        }
    }

    public int getSize(){
        return this.size;
    }


    private class Node{
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
