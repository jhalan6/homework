package DS_application;

import DS_source.Stack;
/**
 *【问题描述】
 *以一个 m*n 的长方阵表示迷宫,0 和 1 分别表示迷宫中的通路和障碍。
 *设计一个程序,对任意设 定的迷宫,求出一条从入口到出口的通路,或得出没有通路的结论。
 *【基本要求】
 *首先实现一个以链表作存储结构的栈类型,然后编写一个求解迷宫的非递归程序。
 *求得的通路以 三元组(i,j,d)的形式输出,其中(i,j)指示迷宫中的一个位置(行号和列号),
 * 表示走到下一 位置的方向(对于迷宫中任一位置,均有下、右、上、左四个方向来走出下一个位置,
 *这四个方向可 分别编号为 1,2,3,4)。例如,对于下面测试数据给出的迷宫,输出的一条通路为:
 *(1,1,1 ),(2,1,1), (3,1,1),(4, 1, 1),(5, l, 2),(5, 2, 2),(5, 3, 1),...。
 *实验数据：8*9
 *00100010
 *00100010
 *00001101
 *01110010
 *00010000
 *01000101
 *01111001
 *11000101
 *11000000
 **/
/*
    迷宫问题的破解:最初,在破解器内部复制了一份完整的迷宫,作为破解依据

    之后根据优先级,依次向左,向下,向右,向上这些方向.

    在选择方向的时候,用栈记录来时的路,不会走回头路(即使那个方向的优先级比较高)

    走到死胡同的时候,将当前位置在私有地图中标为墙,弹栈.

    之后不断循环以上的操作,这样在破解迷宫的时候,内部迷宫的可走范围不断变小.

    但这养的策略会带了一个问题,当遇到一片区域可以行走的时候,会出现死循环,即LabyBraker开始在一个4格区域内转圈

    为了避免这个问题,在每一步前进的时候,进行一次校验,如果最近的4步,和这几步前面的4步走的位置一摸一样,则判定为死循环状态

    在这几个位置,任意一个位置标为墙,把死循环的几步弹栈,然后即可跳出死循环状态

    根据这样的方式,可以获得最终的结果
 */
public class Labyrinth{
    //use to check whether this answer is right
    public static void main(String[] args){
        LabyBreaker breaker=new LabyBreaker();
        breaker.inputLabyrinth("00100010*00100010*00001101*01110010*00010000*01000101*01111001*11000101*11000000",1,1,8,9);
        breaker.breakLaby();
        breaker.getResult(); 
//        breaker.clear();
//        breaker.getResult(); 
    }
}

/**
 * Location is a class to note a place.
 * Provide several method to get relative location
 * Provide several method to check whether two location is the same location
 */
class Location{
    private int x,y;
    public Location(int x,int y){
        this.x=x;
        this.y=y;
    }
    public Location getLeft(){
        return new Location(x-1,y);   
    }
    public Location getRight(){
        return new Location(x+1,y);   
    }
    public Location getDown(){
        return new Location(x,y+1);   
    }
    public Location getUp(){
        return new Location(x,y-1);   
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean isHere(Location compare){
        return (x==compare.getX()&&y==compare.getY());
    }
    public void gotoLocation(Location loc){
        this.x=loc.getX();
        this.y=loc.getY();
    }
    public Location getACopy(){
        return new Location(x,y);
    }
    public String toString(){
        return "坐标：X＝"+x+"Y="+y;
    }
}

/**
 * LabyBreaker contains the main Algorithm
 */
class LabyBreaker{
    private int[][] Laby;
    private int X,Y;
    private String faceTo;                                  //this implements can't record the move direction now
    private Stack<Location> stack=new Stack<Location>(100); //note where the breaker go
    private Location loc_start,loc_aim;
    public void inputLabyrinth(String s,int startX,int startY,int aimX,int aimY){
        readXY(s);
        initLabyArrayWithWall(s);
        loc_start=new Location(startX,startY);
        loc_aim=new Location(aimX,aimY);
    }
    public void getResult(){
        Stack<Location> stack_temp=new Stack<Location>(100); 
        while(!stack.isEmpty())
            stack_temp.push(stack.pop());
        while(!stack_temp.isEmpty()){
            System.out.println("Step:"+(stack_temp.checkTop()).getX()+","+stack_temp.checkTop().getY());
            stack.push(stack_temp.pop());
        }
    }
    public void breakLaby(){
        if(Laby==null){
            System.out.println("There is no Labyrinth!!");
            return;
        } 
        Location loc_now=loc_start.getACopy(); 
        Location[] direction=new Location[4];
        stack.push(new Location(0,0));
        while(!loc_now.isHere(loc_aim)){
            for(int i=0;i<4;++i){
                direction[i]=null;
            }
//            System.out.println(loc_now);
            boolean hadgone=false;
            //左可走
            if(Laby[loc_now.getY()][loc_now.getX()-1]==0){
                direction[0]=loc_now.getLeft(); 
                //System.out.println("能左");
            }
            //下可走
            if(Laby[loc_now.getY()+1][loc_now.getX()]==0){
                direction[1]=loc_now.getDown(); 
                //System.out.println("能下");
            }
            //右可走
            if(Laby[loc_now.getY()][loc_now.getX()+1]==0){
                direction[2]=loc_now.getRight(); 
                //System.out.println("能右");
            }
            //上可走
            if(Laby[loc_now.getY()-1][loc_now.getX()]==0){
                direction[3]=loc_now.getUp(); 
                //System.out.println("能上");
            }
            for(int i=0;i<4;i++){
                //System.out.println("某方位"+direction[i]);
                //System.out.println("栈顶"+stack.checkTop());
                if(direction[i]!=null&&direction[i].isHere(stack.checkTop())){
                    direction[i]=null;
                }            
            } 
            stack.push(loc_now.getACopy());
            for(Location loc_temp:direction){
                if(loc_temp!=null){
                    loc_now.gotoLocation(loc_temp);
                    hadgone=true;
                    break;
                } 
            }
            if(!hadgone) {
//                System.out.println("执行过！");
                Laby[loc_now.getY()][loc_now.getX()]=1;
                stack.pop();
                loc_now= stack.pop();
            }
            Location temp[]=new Location[8];
            int icheck;
            for(icheck=0;icheck<8;++icheck){
                if(!stack.isEmpty()){
                        temp[icheck]=stack.pop();
                        //System.out.println(temp[icheck]);
                        }
                else
                    break;
            }
            if(icheck==8&&temp[0].isHere(temp[4])&&temp[1].isHere(temp[5])&&temp[2].isHere(temp[6])&&temp[3].isHere(temp[7])){
                Laby[temp[2].getY()][temp[2].getX()]=1;
                //System.out.println("修正！");//note whether had get over a wrong situation
            }else{
                for(icheck--;icheck>=0;icheck--){
                    stack.push(temp[icheck]);
                }
            }
            //showLaby();
        }
        stack.push(loc_aim.getACopy());
    }
    public void clear(){
        Laby=null;
        loc_start=loc_aim=null;
        stack=null;
    }
    private void initLabyArrayWithWall(String labyString){
        Laby=new int[Y+2][X+2];
        //System.out.println(""+(X+2)+"...."+(Y+2));
        //制作数组中的围墙
        for(int i=0;i<X+2;++i){
            Laby[0][i]=1;
            Laby[Y+1][i]=1;
        }
        for(int i=0;i<Y+2;++i){
            Laby[i][0]=1;
            Laby[i][X+1]=1;
        }
        //把迷宫放在围墙之中
        int writeX=1,writeY=1;
        for(char temp:labyString.toCharArray()){
            if(temp=='*'){
                writeY++;
                writeX=1;
                continue;
            }
            if(temp=='0')
                Laby[writeY][writeX]=0;
            else
                Laby[writeY][writeX]=1;
            ++writeX;
        }
    }
    private void readXY(String labyString){
        int itemp=0; 
        //用来读取迷宫的宽度
        for(char temp:labyString.toCharArray()){
            if(temp!='*'){
                itemp++;
            }else{
                X=itemp;
                break;
            }     
        }
        //用来读取迷宫的长度
        itemp=1;
        for(char temp:labyString.toCharArray()){
            if(temp=='*'){
                itemp++;
            }
        }
        Y=itemp;
    }
    //A method for test each step the breaker go
    public void showLaby(){
        for(int[] a:Laby){
            for(int b:a){
                System.out.print(b);
            }
            System.out.println();
        }
    }
}
