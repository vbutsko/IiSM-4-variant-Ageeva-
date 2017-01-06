import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.Arrays;

/**
 * Created by Lena on 16.10.2016.
 */
public class Normal extends Distribution {

    private int m;
    private int s_2 ;

    public Normal(int n, int m, int s_2) {
        super(n);
        this.m = m;
        this.s_2 = s_2;
    }

    public void generate() {
        for (int i = 0; i < n; ++i) {
            double res = 0.0;
            for (int j = 0; j < 12; ++j) {
                res += Math.random();
            }
            res -= 6;
            array[i] = m + res * Math.sqrt(s_2);
        }
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
        NormalDistribution normalDistribution = new NormalDistribution(m,Math.sqrt(s_2));
        double[] arr = super.array.clone();
        double delta=1.36;
        Arrays.sort(arr);
        double max = 0;
        for (int i = 0; i < arr.length-1; i++)
        {
            max = Math.max(max,Math.abs(normalDistribution.cumulativeProbability(arr[i])-F(arr[i])) );
            max = Math.max(max,Math.abs(normalDistribution.cumulativeProbability(arr[i+1])-F(arr[i])) );
        }
        if(Math.sqrt(arr.length)*max<delta) {

            return true;
        }

        return false;
    }

    public static void main(String[] args) {
       Normal normal = new Normal(1000, 0, 9);
        normal.generate();
        normal.print();
        System.out.println("real M: " + normal.M());
        System.out.println("M: " + normal.m);
        System.out.println("real D: " + normal.D());
        System.out.println("D: " + normal.s_2);
        normal.checkKolmogorov();
        int sum=0;
        for(int i=0;i<1000;i++){
            normal.generate();
            if(normal.checkKolmogorov())
                sum++;
        }
        System.out.println(sum*1./1000);

    }
}
