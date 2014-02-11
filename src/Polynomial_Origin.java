public class Polynomial_Origin {
	private int[] coef; // coefficients
	private int deg; // degree of Polynomial_Origin (0 for the zero Polynomial_Origin)

	/**
	 * 1x^12 - 24x^11 + 276x^10 - 2007x^9 + 10229x^8 - 3827704x^7 +
	 * 10609123462x^6 - 217514201x^5 + 316228511x^4 - 314838x^3 + 186053x^2 -
	 * 48858x
	 */

	/**
	 * Creates the constant Polynomial_Origin P(x) = 1.
	 */
	public Polynomial_Origin() {
		coef = new int[1];
		coef[0] = 1;
		deg = 0;
	}

	/**
	 * Creates the linear Polynomial_Origin of the form P(x) = x + a.
	 */
	public Polynomial_Origin(int a) {
		coef = new int[2];
		coef[1] = 1;
		coef[0] = a;
		deg = 1;
	}

	/**
	 * Creates the Polynomial_Origin P(x) = a * x^b.
	 */
	public Polynomial_Origin(int a, int b) {
		coef = new int[b + 1];
		coef[b] = a;
		deg = degree();
	}

	/**
	 * Return the degree of this Polynomial_Origin (0 for the constant Polynomial_Origin).
	 */
	public int degree() {
		int d = 0;
		for (int i = 0; i < coef.length; i++)
			if (coef[i] != 0)
				d = i;
		return d;
	}

	/**
	 * Return the sum of this Polynomial_Origin and b, i.e., return c = this + b.
	 */
	public Polynomial_Origin plus(Polynomial_Origin b) {
		Polynomial_Origin a = this;
		Polynomial_Origin c = new Polynomial_Origin(0, Math.max(a.deg, b.deg));
		for (int i = 0; i <= a.deg; i++)
			c.coef[i] += a.coef[i];
		for (int i = 0; i <= b.deg; i++)
			c.coef[i] += b.coef[i];

		c.deg = c.degree();
		return c;
	}

	/**
	 * Return the difference of this Polynomial_Origin and b, i.e., return (this - b).
	 */
	public Polynomial_Origin minus(Polynomial_Origin b) {
		Polynomial_Origin a = this;
		Polynomial_Origin c = new Polynomial_Origin(0, Math.max(a.deg, b.deg));
		for (int i = 0; i <= a.deg; i++)
			c.coef[i] += a.coef[i];
		for (int i = 0; i <= b.deg; i++)
			c.coef[i] -= b.coef[i];

		c.deg = c.degree();
		return c;
	}

	/**
	 * Return the product of this Polynomial_Origin and b, i.e., return (this * b).
	 */
	public Polynomial_Origin times(Polynomial_Origin b) {
		Polynomial_Origin a = this;
		Polynomial_Origin c = new Polynomial_Origin(0, a.deg + b.deg);
		for (int i = 0; i <= a.deg; i++)
			for (int j = 0; j <= b.deg; j++)
				c.coef[i + j] += (a.coef[i] * b.coef[j]);
		c.deg = c.degree();
		return c;
	}

	/**
	 * Return the composite of this Polynomial_Origin and b, i.e., return this(b(x)) -
	 * compute using Horner's method.
	 */
	public Polynomial_Origin compose(Polynomial_Origin b) {
		Polynomial_Origin a = this;
		Polynomial_Origin c = new Polynomial_Origin(0, 0);
		for (int i = a.deg; i >= 0; i--) {
			Polynomial_Origin term = new Polynomial_Origin(a.coef[i], 0);
			c = term.plus(b.times(c));
		}
		return c;
	}

	/**
	 * Return true whenever this Polynomial_Origin and b are identical to one another.
	 */
	public boolean equals(Polynomial_Origin b) {
		Polynomial_Origin a = this;
		if (a.deg != b.deg)
			return false;
		for (int i = a.deg; i >= 0; i--)
			if (a.coef[i] != b.coef[i])
				return false;
		return true;
	}

	/**
	 * Evaluate this Polynomial_Origin at x, i.e., return this(x).
	 */
	public int evaluate(int x) {
		int p = 0;
		for (int i = deg; i >= 0; i--)
			p = coef[i] + (x * p);
		return p;
	}

	/**
	 * Return the derivative of this Polynomial_Origin.
	 */
	public Polynomial_Origin differentiate() {
		if (deg == 0)
			return new Polynomial_Origin(0, 0);
		Polynomial_Origin deriv = new Polynomial_Origin(0, deg - 1);
		deriv.deg = deg - 1;
		for (int i = 0; i < deg; i++)
			deriv.coef[i] = (i + 1) * coef[i + 1];
		return deriv;
	}

	/**
	 * Return a textual representation of this Polynomial_Origin.
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

	public static void main(String[] args) {
		Polynomial_Origin zero = new Polynomial_Origin(0, 0);

		Polynomial_Origin p1 = new Polynomial_Origin(4, 3);
		System.out.println("p1 =\t\t" + p1);
		Polynomial_Origin p2 = new Polynomial_Origin(3, 2);
		System.out.println("p2 =\t\t" + p2);
		Polynomial_Origin p3 = new Polynomial_Origin(-1, 0);
		System.out.println("p3 =\t\t" + p3);
		Polynomial_Origin p4 = new Polynomial_Origin(-2, 1);
		System.out.println("p4 =\t\t" + p4);
		Polynomial_Origin p = p1.plus(p2).plus(p3).plus(p4); // 4x^3 + 3x^2 - 2x - 1

		Polynomial_Origin q1 = new Polynomial_Origin(3, 2);
		Polynomial_Origin q2 = new Polynomial_Origin(5, 0);
		Polynomial_Origin q = q1.minus(q2); // 3x^2 - 5

		Polynomial_Origin r = p.plus(q);
		Polynomial_Origin s = p.times(q);
		Polynomial_Origin t = p.compose(q);

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

		Polynomial_Origin poly = new Polynomial_Origin();
		System.out.print("Iterativet  =\t");
		for (int k = 0; k <= 3; k++) {
			poly = poly.times(new Polynomial_Origin(-k));
		}

		System.out.println(poly);
	}

}
