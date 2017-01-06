/**
 * Created by wladek on 11/10/16.
 */
public class Function1 implements FuncUnderIntegral {

    private double a, b;

    @Override
    public double function(double x) {
        if( x < a || x > b){
            return 0.;
        }else {
            return Math.cos(x + Math.sin(x));
        }
    }

    public Function1(double a, double b){
        super();
        this.a = a;
        this.b = b;
    }
}
