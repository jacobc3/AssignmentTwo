import java.lang.Long;
public class Polynomial {
	private long[] coef; // coefficients
	private int deg; // degree of polynomial (0 for the zero polynomial)

	/**
	 * 1x^12 - 24x^11 + 276x^10 - 2007x^9 + 10229x^8 - 3827704x^7 +
	 * 10609123462x^6 - 217514201x^5 + 316228511x^4 - 314838x^3 + 186053x^2 -
	 * 48858x
	 */

	/**
	 * Creates the constant polynomial P(x) = 1.
	 */
	public Polynomial() {
		coef = new long[1];
		coef[0] = 1;
		deg = 0;
	}

	/**
	 * Creates the linear polynomial of the form P(x) = x + a.
	 */
	public Polynomial(long a) {
		coef = new long[2];
		coef[1] = 1;
		coef[0] = a;
		deg = 1;
	}

	/**
	 * Creates the polynomial P(x) = a * x^b.
	 */
	public Polynomial(long coef2, int b) {
		coef = new long[b + 1];
		coef[b] = coef2;
		deg = degree();
	}

	/**
	 * Return the degree of this polynomial (0 for the constant polynomial).
	 */
	public int degree() {
		int d = 0;
		for (int i = 0; i < coef.length; i++)
			if (coef[i] != 0)
				d = i;
		return d;
	}

	/**
	 * Return the sum of this polynomial and b, i.e., return c = this + b.
	 */
	public Polynomial plus(Polynomial b) {
		Polynomial a = this;
		Polynomial c = new Polynomial(0, Math.max(a.deg, b.deg));
		for (int i = 0; i <= a.deg; i++)
			c.coef[i] += a.coef[i];
		for (int i = 0; i <= b.deg; i++)
			c.coef[i] += b.coef[i];

		c.deg = c.degree();
		return c;
	}

	/**
	 * Return the difference of this polynomial and b, i.e., return (this - b).
	 */
	public Polynomial minus(Polynomial b) {
		Polynomial a = this;
		Polynomial c = new Polynomial(0, Math.max(a.deg, b.deg));
		for (int i = 0; i <= a.deg; i++)
			c.coef[i] += a.coef[i];
		for (int i = 0; i <= b.deg; i++)
			c.coef[i] -= b.coef[i];

		c.deg = c.degree();
		return c;
	}

	/**
	 * Return the product of this polynomial and b, i.e., return (this * b).
	 */
	public Polynomial times(Polynomial b) {
		Polynomial a = this;
		Polynomial c = new Polynomial(0, a.deg + b.deg);
		for (int i = 0; i <= a.deg; i++)
			for (int j = 0; j <= b.deg; j++)
				c.coef[i + j] += (a.coef[i] * b.coef[j]);
		c.deg = c.degree();
		return c;
	}

	/**
	 * Return the composite of this polynomial and b, i.e., return this(b(x)) -
	 * compute using Horner's method.
	 */
	public Polynomial compose(Polynomial b) {
		Polynomial a = this;
		Polynomial c = new Polynomial(0, 0);
		for (int i = a.deg; i >= 0; i--) {
			Polynomial term = new Polynomial(a.coef[i], 0);
			c = term.plus(b.times(c));
		}
		return c;
	}
	
	public static Polynomial factorial(long n){
		Polynomial poly = new Polynomial(0); //<----x
		//StdOut.print("poly is "+poly);
		for(long i = 1; i <=n-1; i++){
			poly = poly.times((new Polynomial(0)).minus(new Polynomial(i, 0)));
			//StdOut.print(" \n---------------- "+(new Polynomial(0)).minus(new Polynomial(i, 0))  );
			//StdOut.print(" \n "+poly);
			// poly = x * (x-1)* ... *(x-(n-1))
		}		
		StdOut.println();
		return poly;		
	}
	
	
	

	/**
	 * Return true whenever this polynomial and b are identical to one another.
	 */
	public boolean equals(Polynomial b) {
		Polynomial a = this;
		if (a.deg != b.deg)
			return false;
		for (int i = a.deg; i >= 0; i--)
			if (a.coef[i] != b.coef[i])
				return false;
		return true;
	}

	/**
	 * Evaluate this polynomial at x, i.e., return this(x).
	 */
	public long evaluate(int x) {
		long p = 0;
		for (int i = deg; i >= 0; i--)
			p = coef[i] + (x * p);
		return p;
	}

	/**
	 * Return the derivative of this polynomial.
	 */
	public Polynomial differentiate() {
		if (deg == 0)
			return new Polynomial(0, 0);
		Polynomial deriv = new Polynomial(0, deg - 1);
		deriv.deg = deg - 1;
		for (int i = 0; i < deg; i++)
			deriv.coef[i] = (i + 1) * coef[i + 1];
		return deriv;
	}

	/**
	 * Return a textual representationof this polynomial.
	 */
	public String toString() {
		if (deg == 0)
			return "" + coef[0];
		if (deg == 1)
			return coef[1] + "x + " + coef[0];
		String s = coef[deg] + "x^" + deg;
		for (int i = deg - 1; i >= 0; i--) {
			if (coef[i] == 0)
				continue;
			else if (coef[i] > 0)
				s = s + " + " + (coef[i]);
			else if (coef[i] < 0)
				s = s + " - " + (-coef[i]);
			if (i == 1)
				s = s + "x";
			else if (i > 1)
				s = s + "x^" + i;
		}
		return s;
	}
/*
	public static void main(String[] args) {
		Polynomial zero = new Polynomial(0, 0);

		Polynomial p1 = new Polynomial(10609123462L, 3);
		System.out.println("p1 =\t\t" + p1);
		Polynomial p2 = new Polynomial(3, 2);
		System.out.println("p2 =\t\t" + p2);
		Polynomial p3 = new Polynomial(-10609123433462L, 0);
		System.out.println("p3 =\t\t" + p3);
		Polynomial p4 = new Polynomial(-2, 1);
		System.out.println("p4 =\t\t" + p4);
		Polynomial p = p1.plus(p2).plus(p3).plus(p4); // 4x^3 + 3x^2 - 2x - 1

		Polynomial q1 = new Polynomial(3, 2);
		Polynomial q2 = new Polynomial(5, 0);
		Polynomial q = q1.minus(q2); // 3x^2 - 5

		Polynomial r = p.plus(q);
		Polynomial s = p.times(q);
		Polynomial t = p.compose(q);

		System.out.println("zero(x) =\t" + zero);
		System.out.println("p(x) =\t\t" + p);
		System.out.println("q(x) =\t\t" + q);
		System.out.println("p(x) + q(x) = \t" + r);
		System.out.println("p(x) * q(x) = \t" + s);
		System.out.println("p(q(x))     = \t" + t);
		System.out.println("0 - p(x)    = \t" + zero.minus(p));
		System.out.println("p(3)        = \t" + p.evaluate(3));
		System.out.println("p'(x)       = \t" + p.differentiate());
		System.out
				.println("p''(x)      = \t" + p.differentiate().differentiate());

		Polynomial poly = new Polynomial();
		System.out.print("Iterative  =\t");
		for (long k = 0; k <= 3462L; k++) {
			poly = poly.times(new Polynomial(-k));
		}

		System.out.println(poly);
	}
	
	*/

}
