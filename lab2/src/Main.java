import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by wladek on 10/4/16.
 */


public class Main {

    public static double expectedBinValue(int m, double p){
        return m*p;
    }

    public static double expectedGeomValue(double p){
        return 1/p;
    }

    public static double dispersionBin(int m, double p){
        return m*p*(1-p);
    }

    public static double dispersionGeom(double p){
        return (1-p)/(p*p);
    }

    public static double dispersion(int[] array, int n){
        double m = expectedValue(array, n);
        double sum = 0.0;
        for(int i = 0; i < n; i++){
            sum += Math.pow(array[i] - m,2);
        }
        return sum/n;
    }
    public static double expectedValue(int[] array, int n){
        double sum = 0.0;
        for(int i = 0; i < n; i++){
            sum+=array[i];
        }
        return sum/n;
    }

    public static void main(String[] args) throws FileNotFoundException {
        int n = 1000;
        double p1 = 0.2;
        double M = Math.pow(2, 31);
        int m = 4;
        double p2 = 0.3;

        int l1 = 0;
        int l2 = 0;
        Random random = new Random();
        for (int k=0; k<1000; k++) {
            int[] binomial = new int[n];
            for (int i = 0; i < n; i++) {
                int t = 0;
                for (int j = 0; j < m; j++) {
                    t += Math.random() < p1 ? 1 : 0;
                }
                binomial[i] = t;
            }
            System.out.println("teoretic m: " + expectedBinValue(m, p1) + "; real: " + expectedValue(binomial, n));
            System.out.println("teoretic dispersion: " + dispersionBin(m, p1) + "; real: " + dispersion(binomial, n));
            if(MKM.pirsonBinomial(binomial, 7.8, m, p1)){
                l1+=1;
            }

            int geometric[] = new int[1000];
            for (int i = 0; i < n; i++) {
                geometric[i] = (int) Math.ceil(Math.log(random.nextDouble()%(1.*n)) / Math.log(1. - p2));
            }
            System.out.println("teoretic m: " + expectedGeomValue(p2) + "; real: " + expectedValue(geometric, n));
            System.out.println("teoretic dispersion: " + dispersionGeom(p2) + "; real: " + dispersion(geometric, n));
            if(MKM.pirsonGeometric(geometric, 16.919, 10, p2)){
                l2+=1;
            }
        }
        System.out.println("geometr - " + l2 / 1000.0);
        System.out.println("binomeal - " + l1 / 1000.0);
    }
}
