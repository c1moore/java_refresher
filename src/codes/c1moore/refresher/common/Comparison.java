package codes.c1moore.refresher.common;
/**
 * Comparison is an enumeration of the different relative equalities (or inequalities)
 * possible when comparing 2 values.  Comparison hides the nitty-gritty details of how
 * different two items really are (e.g. no need for positive/negative integers) and is more
 * readable and understandable than trying to determine what a negative and positive integer
 * really mean.
 */
public enum Comparison {
	GREATER,
	EQUAL,
	LESS;

	/**
	 * Returns a Comparison from the specified integer value.  The following assumptions
	 * are made about value
	 * 	-	A negative value infers the left-hand operand is less than the right-hand
	 *  	operand => Comparison.LESS
	 *  -	A zero value infers that both operands are equal => Comparison.EQUAL
	 *  -	A positive value infers that the left-hand operand is greater than the right-
	 *  	hand operand => Comparison.GREATER
	 *  
	 * These assumptions align with standard Java conventions.  Therefore, one could use
	 * this method to convert the result from, for example, {@link Comparable#compareTo(Object)}
	 * to a Comparison.
	 *
	 * @param value the integer value to convert to a Comparison
	 *
	 * @return the Comparison inferred from value
	 */
	public static Comparison create(int value) {
		if(value < 0) {
			return Comparison.LESS;
		}

		if(value > 0) {
			return Comparison.GREATER;
		}

		return Comparison.EQUAL;
	}
}