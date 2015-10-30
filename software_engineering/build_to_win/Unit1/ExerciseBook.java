/**第一步： 像阿超那样，花二十分钟写一个能自动生成小学四则运算题目的命令行 “软件”，要求：
 *a) 除了整数以外，还要支持真分数的四则运算。 （例如：  1/6 + 1/8 = 7/24）
 *b) 让程序能接受用户输入答案，并判定对错。 最后给出总共 对/错 的数量。
 *和同学们比较一下各自程序的功能、实现方法的异同等等。
 *第二步，
 *每个同学选一个方向，把程序扩展一下：
 *a) 把程序变成一个网页程序， 用户通过设定参数，就可以得到各种题目。
 *b) 把程序变成一个Windows/Mac/Linux 电脑图形界面的程序 (取决于你目前使用的电脑)
 *c) 把程序变成一个智能手机程序 （你正在用什么手机， 就写那个手机的程序）
 *d) 选一个你从来没有学过的编程语言，试一试实现基本功能。
 *估计做好这个软件需要的时间，并且写出大概的设计步骤和实现算法。
 *可以开始做相关的第二个作业。
 */
import java.lang.Math;
import java.util.Random;
import java.io.InputStream;
public class ExerciseBook{
    public static void main(String[] aregs){
        ProblemFactory factory=new ProblemFactory(10);
        InputStream input=System.in;
        System.out.println(factory.nextProblem());
        int c;
        StringBuffer sb;
        String answer;
        while(true){
            answer=null;
            sb=new StringBuffer();
            try{
                while(true){
                    c=input.read();
                    if((c>='0'&&c<='9')||c=='/'||c=='-')
                        sb.append((char)c);
                    else
                        break;
                }
                answer=sb.toString();
            }catch(Exception e){
                System.out.println(e);
            }
            System.out.println(factory.checkAnswer(answer)); 
            System.out.println(factory.nextProblem());
        }
    }
}
class ProblemFactory{
    private final int max;
    private Problem problem;
    public ProblemFactory(int max){
        this.max=max;
    }
    public String nextProblem(){
        problem=new Problem(max);
        return ""+problem;
    }
    public String checkAnswer(String answer){
        return problem.checkAnswer(answer); 
    }
}
class Problem{
    private Random random;
    private boolean isFraction;
    private int numerator_1,numerator_2,denominator_1,denominator_2;
    private int max;
    private char operator;
    private String answer;
    public Problem(int max){
        this.max=max;
        random=new Random();
        numerator_1=random.nextInt(10)+1;
        numerator_2=random.nextInt(10)+1;
        denominator_1=random.nextInt(10)+1;
        denominator_2=random.nextInt(10)+1;
        isFraction=random.nextBoolean();
        operator=getOperator(random.nextInt(3));
        calculate();
        //System.out.println(answer);
    }
    public String toString(){
        return getNumber(numerator_1,denominator_1)+operator+getNumber(numerator_2,denominator_2); 
    }
    public String checkAnswer(String answer){
        //System.out.println("answer:"+answer);
        //System.out.println("right"+this.answer);
        if(answer.equals(this.answer)){
            return "答对了";
        }else{
            System.out.println("答案是："+this.answer);
            return "答错了";
        } 
    }
    private char getOperator(int i){
        switch(i%4){
            case 1:
                return '+';
            case 2:
                return '-';
            case 3:
                return '*';
            case 4:
                return '/';
            default:
                return '+';
        } 
    }
    private void calculate(){
        if(isFraction){
            denominator_1=1;
            denominator_2=1;
        }
        String answer=null;
        switch(operator){
            case'+':
                answer=getNumber(denominator_2*numerator_1+denominator_1*numerator_2,denominator_1*denominator_2);
                break;
            case'-':
                answer=getNumber(denominator_2*numerator_1-denominator_1*numerator_2,denominator_1*denominator_2);
                break;
            case'*':
                answer=getNumber(numerator_2*numerator_1,denominator_1*denominator_2);
                break;
            case'/':
                answer=getNumber(denominator_2*numerator_1,numerator_2*denominator_2);
                break;
        } 
        this.answer=answer;
    }
    private String getNumber(int num_1,int num_2){
        if(num_1%num_2==0){
            return ""+num_1/num_2;
        }
        return ""+(num_1/gcd(Math.abs(num_1),Math.abs(num_2)))+"/"+(num_2/gcd(Math.abs(num_1),Math.abs(num_2)));
    }
    private int gcd(int a,int b) {
        //最大公约数
        if (a<b) 
            return gcd(b,a);
        else if (b==0)
            return a;
        else return gcd(b,a%b);
    }
}
