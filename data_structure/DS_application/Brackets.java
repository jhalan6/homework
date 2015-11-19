package DS_application;

import DS_source.Stack;

/**
 * problem in dataStructurePractice.pdf
 * test2_2:
 * 设一个表达式中可以包含三种括号:“(”和“)”、“[”和“]”、“{”和“}”,并且这三种括号可以按照任意 的次序嵌套使用,考查表达式中的括号是否匹配。
 * addNewCar Tool to check Brackets in a String whether they are matched
 */
public class Brackets {
    /**
     * a static method for String to check Brackets
     *
     * @param toChecked the string to check
     * @return a boolean to show the sesult
     */
    public static boolean checkBrackets(String toChecked) {
        char[] chars = toChecked.toCharArray();
        Stack<Character> stack = new Stack<Character>(100);
        for (char c : chars) {
            if (c == '(')
                stack.push('(');
            else if (c == '{')
                stack.push('{');
            else if (c == '[')
                stack.push('[');
            else if (c == ')') {
                if (stack.isEmpty() || stack.pop() != '(')
                    return false;
            } else if (c == ']') {
                if (stack.isEmpty() || stack.pop() != '[')
                    return false;
            } else if (c == '}') {
                if (stack.isEmpty() || stack.pop() != '{')
                    return false;
            }
        }
        return stack.isEmpty();
    }
}
