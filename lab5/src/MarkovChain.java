/**
 * Created by wladek on 20/11/16.
 */
public class MarkovChain {

    public static int[] generate(int N,double pi[], double P[][]){
        int chain[] = new int[N];
        double r = Math.random();
        double sum=0.;
        int m = pi.length;
        for (int i = 0; i < m; i++) {
            sum += pi[i];
            if (r <= sum) {
                chain[0] = i;
                break;
            }
        }
        for(int i=1;i<N;i++){
            r = Math.random();
            sum = 0.0;
            for (int j = 0; j < m; j++) {
                sum += P[chain[i-1]][j];
                if (r <= sum) {
                    chain[i] = j;
                    break;
                }
            }
        }
        return chain;
    }
}
