package Figure;

public class Value {
    public double Normal;
    public double Promoted;

    public double get(boolean promoted)
    {
        return (promoted?Normal:Promoted);
    }
    public void set(double normal,double promoted)
    {
        this.Normal = normal;
        this.Promoted = promoted;
    }
}
