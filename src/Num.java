public class Num {
    public char Sign;
    public int Number;
    public boolean isSign;
    public Num(boolean _isSign,char _Sign,int _Number)
    {
        Number=_Number;
        isSign=_isSign;
        Sign=_Sign;
    }
    public void print()
    {
        if(isSign) System.out.print(Sign);
        else System.out.print(Number);
        System.out.print(" ");
    }

}
