import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Stack;

public class CalFrame extends JFrame{
    public JFrame frame;
    public JLabel label;
    public JPanel panel;
    public String value;
    public DataProcess dp;
    public JButton makeButton(String str)
    {
        JButton jb = new JButton(str);
        jb.setBorder(BorderFactory.createCompoundBorder());
        jb.setBackground(new Color(225,225,225));
        jb.setFont(new Font("黑体",Font.BOLD,30));
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String save =value.trim();
                if(jb.getText().equals("<-"))
                {
                    if(value.equals(" "));
                    else
                    value = value.substring(0,value.length()-1);
                }
                else if(jb.getText().equals("C"))
                {
                    value=" ";
                }
                else if(jb.getText().equals("="))
                {
                    value=new String(save);
                    String tmp = value.trim();

                    try{
                        if(tmp.substring(0,1).equals("+")||tmp.substring(0,1).equals("-"))
                            tmp="0"+tmp;
                        System.out.println("tmp");
                        System.out.println(tmp);
                        value=dp.Process(tmp);
                        System.out.println(value);
                    }
                    catch (Exception e1)
                    {
                        System.out.println("save");
                        System.out.println(save);
                        value = new String(save);
                    }finally {
                        value = " "+value;
                        label.setText(value);
                    }


                }
                else{
                    value = value+jb.getText();
                }
                label.setText(value);
            }
        });
        return jb;
    }

    public CalFrame()
    {
        value = new String();
        frame = new JFrame("CALCULATOR");
        frame.getContentPane().setBackground(new Color(205,205,205));
        dp = new DataProcess();
        frame.setLayout(new GridBagLayout());
        value = " ";

        label = new JLabel(value);
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setOpaque(true);
        //label.setBorder(BorderFactory.createLoweredBevelBorder());
        label.setBackground(new Color(205,205,205));
        label.setFont(new Font("黑体",Font.BOLD,45));

        panel=new JPanel();
        panel.setLayout(new GridLayout(5,4,5,5));

        GridBagConstraints gbc  = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        label.setSize(180,20);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 180;
        gbc.ipady=120;

        gbc.fill = GridBagConstraints.BOTH;

        frame.add(label,gbc);

        panel.add(makeButton("("));
        panel.add(makeButton(")"));
        panel.add(makeButton("<-"));
        panel.add(makeButton("C"));
        panel.add(makeButton("7"));    //添加按钮
        panel.add(makeButton("8"));
        panel.add(makeButton("9"));
        panel.add(makeButton("/"));
        panel.add(makeButton("4"));
        panel.add(makeButton("5"));
        panel.add(makeButton("6"));
        panel.add(makeButton("*"));
        panel.add(makeButton("1"));
        panel.add(makeButton("2"));
        panel.add(makeButton("3"));
        panel.add(makeButton("-"));
        panel.add(makeButton("C"));
        panel.add(makeButton("0"));
        panel.add(makeButton("="));
        JButton plus = makeButton("+");
        panel.add(plus);
        panel.setBackground(new Color(205,205,205));
        gbc.gridy = 1;
        gbc.ipady = 200;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight=GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5,0,5,0);
        frame.add(panel,gbc);
        frame.setBounds(400,300,450,650);

    }
    public void run()
    {
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String []args)
    {
        CalFrame main = new CalFrame();
        main.run();

    }


















    public class Dps {

        public Stack<Num> stack;
        public Stack<Character>op;
        public Stack<Integer>cal;
        public HashMap<Character,Integer> map;
        public Dps()
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



}
