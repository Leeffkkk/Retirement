package pkgEmpty;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pkgCore.Retirement;

public class TestRetirement {
	@Test
	public void TestTotalAmountToSave() {
		
		Retirement retirement = new Retirement(40, 7, 20, 2, 10000, 2642);
		
		assertEquals(1454485.55, Math.abs(retirement.TotalAmountToSave()), 0.01);
	}
	
	@Test
	public void TestMonthlySavings() {
		
		Retirement retirement = new Retirement(40, 7, 20, 2, 10000, 2642);

		assertEquals(554.13, Math.abs(retirement.MonthlySavings()), 0.01);
	}
}
