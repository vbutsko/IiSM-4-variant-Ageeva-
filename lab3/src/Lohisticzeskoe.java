import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.LogisticDistribution;

import java.util.Arrays;

/**
 * Created by wladek on 11/8/16.
 */
public class Lohisticzeskoe extends Distribution {
    private int m;
    private int s_2;
    private int a;
    private int b;
    public Lohisticzeskoe(int n, int m, int s_2, int a, int b) {
        super(n);
        this.m = m;
        this.s_2 = s_2;
        this.a = a;
        this.b = b;
    }

    private void generate() {
        double[] array = randomArray(n);
        for (int i = 0; i < n; ++i) {
            super.array[i] = F1(array[i]);
        }
    }
    private double F1(double y){
        return a + b*1.0*Math.log(y/(1-y));
    }
    private double F(double arri){
        int sum = 0;
        for(int i=0; i<n;i++){
            if(array[i]<=arri)
                sum++;
        }
        return sum*1./n;
    }

    private boolean checkKolmogorov() {
        LogisticDistribution logisticDistribution = new LogisticDistribution(a,b);
        double[] arr = super.array.clone();
        double delta=1.36;
        Arrays.sort(arr);
        double max = 0;
        for (int i = 0; i < arr.length-1; i++)
        {
            max = Math.max(max,Math.abs(logisticDistribution.cumulativeProbability(arr[i])-F(arr[i]) ));
            max = Math.max(max,Math.abs(logisticDistribution.cumulativeProbability(arr[i+1])-F(arr[i]) ));
        }
        if(Math.sqrt(arr.length)*max<delta) {

            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        Lohisticzeskoe lohisticzeskoe = new Lohisticzeskoe(1000, 0, 1, 2, 3);
        lohisticzeskoe.generate();
        lohisticzeskoe.print();
        System.out.println("real M: " + lohisticzeskoe.M());
        System.out.println("M: " + lohisticzeskoe.a);
        System.out.println("real D: " + lohisticzeskoe.D());
        System.out.println("D: " + Math.pow(lohisticzeskoe.b * 3.141592, 2)/3.0);
        lohisticzeskoe.checkKolmogorov();
        int sum=0;
        for(int i=0;i<1000;i++){
            lohisticzeskoe.generate();
            if(lohisticzeskoe.checkKolmogorov())
                sum++;
        }
        System.out.println(sum*1./1000);
    }
}
