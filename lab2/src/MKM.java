import java.util.Arrays;

/**
 * Created by wladek on 10/4/16.
 */
public class MKM {
    public static double[] mkm(int n, double alpha_0, double beta, double M){
        double[] alpha = new double[n];
        for(int i = 0; i < n; i++){
            alpha[i] = ( beta * alpha_0 - M * ((int) (beta * alpha_0 / M) ));
            alpha_0 = alpha[i];
            alpha[i]/=M;
        }
        return alpha;
    }
    public static boolean pirsonBinomial(int[] a, double delta, int K, double p1 ){
        double hi2=0.;
        int m=4;
        int [] v = new int [m];
        for(int i = 0;i < m; i++)
        {
            v[i]=0;
            for(int j = 0; j < a.length; j++)
            {
                if(a[j] ==i)
                {
                    v[i]++;
                }
            }

            double p=fact(m)*Math.pow(p1,i)*Math.pow(1.-p1,m-i)/(fact(m-i)*fact(i));
            hi2 += (Math.pow(v[i] - (a.length*p), 2.0)) / (a.length*p);
        }
        if (hi2 < delta) {
            return true;
        }
        return false;
    }

    static double fact(int a){
        double fact = 1.;
        for (int i= 1; i<=a; i++){
            fact=fact*i;
        }
        return fact;
    }

    public static boolean pirsonGeometric(int[] a, double delta, int K, double p1 ){
        double kk = 1;
        double[] v = new double[K];
        for(int i = 0; i < a.length; ++i) {
            int start = 0;
            for(int j = 0; j < K-1; ++j) {
                if(start <= a[i] && start+kk >= a[i]) {
                    ++v[j];
                    break;
                }
                else if(a[i]>K-1) {
                    ++v[K-1];
                    break;
                }
                start+=kk;
            }
        }

        double ver = 1.0;
        double sum = 0.0;
        for(int i = 0; i <K; ++i) {
            if(i==K-1){
                sum += Math.pow(v[i]-a.length*ver, 2)/(a.length*ver);
                continue;
            }
            double p = Math.pow(1.-p1, kk*i) - Math.pow(1.-p1, kk*(i+1));
            ver-=p;
            sum += Math.pow(v[i]-a.length*p, 2)/(a.length*p);
        }
        return sum < delta;
    }
}
