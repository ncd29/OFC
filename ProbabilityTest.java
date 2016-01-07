import static org.junit.Assert.*;

import org.junit.Test;


public class ProbabilityTest {

	@Test
	public void test() {
		System.out.println(Probability.combinations(47, 12));
		System.out.println(Probability.summation(47, 12, 3, 11));
	}

}
