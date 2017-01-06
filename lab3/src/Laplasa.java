import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.LaplaceDistribution;
import org.apache.commons.math3.distribution.TDistribution;

import java.util.Arrays;

/**
 * Created by wladek on 11/8/16.
 */
public class Laplasa extends Distribution {
    private int m;
    private int s_2;
    private int a;


    public Laplasa(int n, int m, int s_2, int a) {
        super(n);
        this.m = m;
        this.s_2 = s_2;
        this.a = a;
    }



    private void generate() {
        double[] array = randomArray(n);
        for (int i = 0; i < n; ++i) {
            super.array[i] = F1(array[i]);
        }
    }
    private double F1(double y){
       if(y < 0.5){
           return 1.0/a*Math.log(2*y);
       }else{
           return -1.0/a*Math.log(2*(1-y));
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
        LaplaceDistribution laplaceDistribution = new LaplaceDistribution(0, 1./a);
        double[] arr = super.array.clone();
        double delta=1.36;
        Arrays.sort(arr);
        double max = 0;
        for (int i = 0; i < arr.length-1; i++)
        {
            max = Math.max(max,Math.abs(laplaceDistribution.cumulativeProbability(arr[i])-F(arr[i]) ));
            max = Math.max(max,Math.abs(laplaceDistribution.cumulativeProbability(arr[i+1])-F(arr[i]) ));
        }
        if(Math.sqrt(arr.length)*max<delta) {

            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        Laplasa laplasa = new Laplasa(1000, 0, 1, 2);
        laplasa.generate();
        laplasa.print();
        System.out.println("real M: " + laplasa.M());
        System.out.println("M: " + 0);
        System.out.println("real D: " + laplasa.D());
        System.out.println("D: " + 2.0/(laplasa.a*laplasa.a));
        laplasa.checkKolmogorov();
        int sum=0;
        for(int i=0;i<1000;i++){
            laplasa.generate();
            if(laplasa.checkKolmogorov())
                sum++;
        }
        System.out.println(sum*1./1000);
    }

}
