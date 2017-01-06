import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by wladek on 9/27/16.
 */



public class Main1 {

    public static double[] mkm(int n, double alpha_0, double beta, double M){
        double[] alpha = new double[n];
        for(int i = 0; i < n; i++){
            alpha[i] = ( beta * alpha_0 - M * ((int) (beta * alpha_0 / M) ));
            alpha_0 = alpha[i];
            alpha[i]/=M;
        }
        return alpha;
    }

    public static double[] mmm(int n, double[] beta, double[] gamma, double M, int K){
        double[] alpha = new double[n];
        int index;
        for(int i = 0; i < n; i++){
            index = (int)(gamma[i] * K);
            alpha[i] = beta[index];
            beta[index] = beta[i + K];
        }
        return alpha;
    }

    public static void kolmogorov(double[] mas, int N,  PrintWriter printWriter){
        double[] arr = mas.clone();
        Arrays.sort(arr);
        double delta = 67.5048;
        double max = 0;
        for (int i = 0; i < N; i++)
        {
            max = Math.max(max,Math.abs((1.0 / N) * (i)-arr[i]) );
            max = Math.max(max,Math.abs((1.0 / N) * (i + 1)-arr[i]) );
        }
        if(Math.sqrt(N)*max<delta)
            printWriter.println("Kolmogorov true");
        else
            printWriter.println("Kolmogorov false");
    }

    public static void pirson(double[] mas, int N, PrintWriter printWriter){
        double[] arr = mas.clone();
        double delta = 67.5048;
        double hi2=0;
        int K = 48;
        int [] v = new int [K];
        for(int i = 0;i < K; i++)
        {
            int t = 0;
            for(int j = 0; j < arr.length; j++)
            {
                double g1 = i*(1.0/K);
                double g2 = (i+1)*(1.0/K);
                if(arr[j] > g1 && arr[j] <= g2)
                {
                    t++;
                }
            }
            v[i]=t;
            hi2 += (Math.pow(v[i] - (N*1. / K), 2.0)) / (N*1. / K);
        }
        if (hi2 < delta)
            printWriter.println("Pirson true");
        else
            printWriter.println("Pirson false");
    }
    public static void main(String[] args) throws FileNotFoundException {
        int n = 1000;
        double M = Math.pow(2, 31);
        Scanner scannerInput = new Scanner(new File("input.txt"));
        double l0 = scannerInput.nextDouble();
        double b = scannerInput.nextDouble();
        int K = scannerInput.nextInt();

        //     1
        PrintWriter printWriter = new PrintWriter(new File("output1.txt"));
        double[] alpha = mkm(n, l0, b, M);
        kolmogorov(alpha, n, printWriter);
        pirson(alpha, n, printWriter);
        for(int i = 0; i < n; i++){
            printWriter.println(alpha[i]);
        }
        printWriter.close();


        //    2
        double l01 = 131075;
        double b1 = 131075;
        double[] gamma = alpha;
        double[] beta = mkm(n+K, l01, b1, M);
        printWriter = new PrintWriter(new File("output2.txt"));
        alpha = mmm(n, beta, gamma, M, K);
        kolmogorov(alpha, n, printWriter);
        pirson(alpha, n, printWriter);
        for(int i = 0; i < n; i++){
            printWriter.println(alpha[i]);
        }
        printWriter.close();

    }
}
