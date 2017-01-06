import java.util.Arrays;

/**
 * Created by wladek on 20/11/16.
 */
public class MonteCarloSLAU {
    private double x[];
    private double A[][];
    private double B[][];
    private double f[];
    private int N;

    public MonteCarloSLAU(double[][] a, double[] f, int n) {
        A = a;
        this.f = f;
        N = n;
        x=new double[N];
        B = new double[N][N];
        for(int i=0; i<N;i++){
            for(int j=0; j<N; j++){
                B[i][j]=(i==j?1.:0.)-A[i][j];
            }
        }
    }

    public void calculate(){
        double pi[]=new double[N];
        Arrays.fill(pi,1./N);
        double p[][]=new double[N][N];
        for(int k=0; k<N; k++)
            Arrays.fill(p[k],1./N);
        int L=7000, K=7000;
        for(int i=0;i<N;i++){
            int h[]=new int[N];
            Arrays.fill(h,0);
            h[i]=1;

            double e[] = new double[L];
            Arrays.fill(e,0.);
            for(int l=0; l<L; l++) {
                int chain[] = MarkovChain.generate(K,pi, p);
                double Q[] = new double[K];
                Arrays.fill(Q, 0.);
                Q[0] = pi[chain[0]] > 0 ? h[chain[0]] / pi[chain[0]] : 0.;

                for (int j = 1; j < K; j++) {
                    if (p[chain[j - 1]][chain[j]] > 0)
                        Q[j] =Q[j-1]* B[chain[j - 1]][chain[j]] / p[chain[j - 1]][chain[j]];
                }
                for(int j=0; j<K; j++)
                    e[l]+=Q[j]*f[chain[j]];
            }

            double sum = 0.;
            for(int l=0; l<L; l++){
                sum+=e[l];
            }
            x[i]=sum/L;
            System.out.println(i+": "+x[i]);
        }
    }

    public static void main(String args[]){
        double f[] = {-3,1,4};
        double A[][]={
                {1.1,-0.1,0.2},
                {0.1,0.5,0.3},
                {-0.3,-0.1,1.3}
        };

        MonteCarloSLAU monteCarloSLAU = new MonteCarloSLAU(A,f,3);
        monteCarloSLAU.calculate();
    }
}
