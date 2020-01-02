import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class DataProcess {
    public static void main(String [] args)
    {
        System.out.println("hello world");
        String s;
        Scanner scanner = new Scanner(System.in);
        s=scanner.next();
        DataProcess dp = new DataProcess();
        String st = dp.Process(s);
    }
    public Stack<Num>stack;
    public Stack<Character>op;
    public Stack<Integer>cal;
    public HashMap<Character,Integer>map;
    public DataProcess()
    {
        stack = new Stack<Num>();
        map = new HashMap<Character, Integer>();
        op = new Stack<Character>();
        cal = new Stack<Integer>();
        map.put('+',1);
        map.put('-',1);
        map.put('*',2);
        map.put('/',2);
        map.put('(',0);
        map.put(')',0);
    }
    public String Process(String str)
    {
        try{
            stack = new Stack<Num>();
            op = new Stack<Character>();
            cal = new Stack<Integer>();
            char []s = str.toCharArray();

            int tmp=0;
            for(int i=0;i<str.length();i++)
            {
                if(s[i]>='0'&&s[i]<='9')
                {System.out.println("if");
                    tmp=0;
                    while(s[i]>='0'&&s[i]<='9')
                    {
                        tmp*=10;
                        tmp+=s[i]-'0';
                        i++;
                        if(i>=str.length())
                            break;
                    }
                    Num num = new Num(false,'.',tmp);
                    stack.push(num);
                    i--;
                }
                else{
                    System.out.println("else");
                    if(s[i]=='(')
                        op.push(s[i]);
                    else if(!op.empty())
                    {
                        char ch = op.peek();
                        if(s[i]=='(')
                        {
                            op.push(s[i]);
                        }
                        else if(ch == '(')
                        {
                            op.push(s[i]);
                        }
                        else if(s[i]==')')
                        {
                            while(!op.empty())
                            {
                                ch=op.peek();
                                op.pop();
                                if(ch=='(') break;
                                Num num = new Num(true,ch,0);
                                stack.push(num);
                            }
                        }
                        else if(map.get(s[i])>map.get(ch))
                        {
                            op.push(s[i]);
                        }
                        else if(map.get(s[i])<=map.get(ch))
                        {
                            System.out.println("elif");
                            while(map.get(s[i])<=map.get(ch)&&!op.empty())
                            {
                                Num num = new Num(true,ch,0);
                                stack.push(num);
                                if(!op.empty());
                                else
                                {
                                    break;
                                }
                                ch=op.peek();
                                op.pop();
                            }
                            op.push(s[i]);
                        }
                    }
                    else {
                        op.push(s[i]);
                        System.out.println(s[i]);
                    }
                }

            }
            System.out.println("done");


            while(!op.empty())
            {
                char ch = op.peek();
                op.pop();
                Num num = new Num(true,ch,0);
                stack.push(num);
            }
            for(Num n:stack)
                n.print();
            System.out.println("");
            for(int i=0;i<stack.size();i++)
            {
                if(stack.get(i).isSign)
                {
                    int tmp1=0;
                    int tmp2=0;
                    Num num = stack.get(i);
                    if(num.Sign=='+')
                    {

                        tmp1=cal.peek();
                        cal.pop();
                        tmp2=cal.peek();
                        cal.pop();
                        tmp1+=tmp2;
                        cal.push(tmp1);
                    }
                    if(num.Sign=='-')
                    {
                        tmp1=cal.peek();
                        cal.pop();
                        tmp2=cal.peek();
                        cal.pop();
                        tmp1-=tmp2;
                        tmp1 = -tmp1;
                        cal.push(tmp1);
                    }
                    if(num.Sign=='*')
                    {
                        tmp1=cal.peek();
                        cal.pop();
                        tmp2=cal.peek();
                        cal.pop();
                        tmp1*=tmp2;
                        cal.push(tmp1);
                    }
                    if(num.Sign=='/')
                    {
                        try{
                            tmp1=cal.peek();
                            cal.pop();
                            tmp2=cal.peek();
                            cal.pop();
                            tmp1=tmp2/tmp1;
                            cal.push(tmp1);
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                            return str;
                        }
                    }

                }
                else{
                    cal.push(stack.get(i).Number);
                }
            }

            System.out.println(cal.peek());


            return cal.peek().toString();
        }catch (Exception e)
        {
            System.out.println("exception");
            System.out.println(e);
            return str;
        }


    }
}
