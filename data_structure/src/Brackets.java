public class Brackets{
    public static boolean checkBrackets(String s){
        char[] chars=s.toCharArray();
        Stack<Character> stack=new Stack<Character>(100);
        for(char c:chars){
            if(c=='(')
                stack.push('(');
            else if(c=='{')
                stack.push('{');
            else if(c=='[')
                stack.push('[');
            else if(c==')') {
                if(stack.isEmpty()||stack.pop()!='(')
                    return false;
                    }
            else if(c==']'){
                if(stack.isEmpty()||stack.pop()!='[')
                    return false;
                    }
            else if(c=='}'){
                if(stack.isEmpty()||stack.pop()!='{')
                    return false;
                    }
        }
        if(stack.isEmpty())
            return true; 
        return false;
    }
    public static void main(String[] args){
        System.out.println(checkBrackets("[(2+3))"));
        System.out.println(checkBrackets("[2+3])"));
        System.out.println(checkBrackets("()}"));
        System.out.println(checkBrackets("()]"));
        System.out.println(checkBrackets("[4(6]7)9"));
    }
}
