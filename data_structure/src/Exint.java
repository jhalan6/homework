public class Exint{
    static enum system{
        Hexadecimal,Binary,Decimal,Octonary; 
        public static int toInt(system to){
            int fun=10;
            switch(to){
                case Hexadecimal:
                    fun=16;
                    break;
                case Binary:
                    fun=2;
                    break;
                case Octonary:
                    fun=8;
                    break;
                case Decimal:
                    fun=10;
                    break;
            }
            return fun;
        }
    }
    public static String function(system to,int i){
        Stack<String> stack=new Stack<String>(100);
        int temp=system.toInt(to);
        while(i!=0){
            stack.push(getRight(i%temp));
            i/=temp;
        }
        StringBuffer s=new StringBuffer();
        while(!stack.isEmpty()){
            s.append(stack.pop());
        }
        return s.toString();   
    }
    private static String getRight(int i){
        if(i>=0&&i<=9)
            return ""+i;
        else 
            return ""+(char)((int)'A'+i-10);
    }
    public static void main(String[] args){
        System.out.println(function(Exint.system.Binary,100)); 
        System.out.println(function(Exint.system.Hexadecimal,15)); 
        System.out.println(function(Exint.system.Octonary,100)); 
    }
}
