import org.apache.commons.math3.distribution.*;

/**
 * Created by wladek on 11/10/16.
 */
public class MonteCarlo {
    public static void calculate1(FuncUnderIntegral funcUnderIntegral, double a, double b, int n){
        double sum = 0.;
        for(int i=0; i < n; i++) {
            double x = Math.random();
            sum+=funcUnderIntegral.function(x*5.*Math.PI/7.) / (1/(5.*Math.PI/7.));
        }
        System.out.println(sum/n);
    }

    public static void calculate2(int n){
        Function2 function = new Function2();
        double sum = 0.;
        for(int i=0; i<n; i++) {
            double x = -3 + 6 * Math.random();
            double y = -3 + 6 * Math.random();
            if(x*x + y*y < 1 || x*x + y*y > 3) {
                i--;
                continue;
            }
            sum+=function.function(x,y)/ (1./6.);
        }
        System.out.println(sum/n);
    }

    public static void main(String[] args) throws Exception
    {
        double a = 0;
        double b = 5./7. * Math.PI;

        FuncUnderIntegral f1 = new Function1(a, b);
        calculate1(f1, a, b, 10000);

        calculate2(8000);
    }
}
