package org.thshsh.text;

import org.junit.Test;

public class DurationFormatUtilsTest {

	@Test
	public void test() {
		
		String format1 = "d'd' H'h 'm'm 's's'";
		
		String format = "[d'd' ][H'h '][m'm '][s's']";
		
		String[] formats = {
				"d'd 'H'h 'm'm 's's'",
				"[d'd '][H'h '][m'm ']s's'",
				"[d'd '][H'h '][m'm '][s's']",
				"[d'd 'H'h 'm'm 's's']",
		};
		
		//long[] durs = {915361000l,9153610l,915361l,9153l};
		
		long dur = 915361000l;
		
		/*System.out.println( DurationFormatUtils.formatDuration(915361000l, format,false));
		
		System.out.println( DurationFormatUtils.formatDuration(9153610l, format,false));
		
		System.out.println( DurationFormatUtils.formatDuration(915361l, format,false));
		
		System.out.println( DurationFormatUtils.formatDuration(9153l, format,false));*/
				
		
		for(int i=0;i<6;i++) {
			dur = dur / 10^i;
			System.out.println(dur);
			for(String f : formats) {
				System.out.println(f+" = "+ DurationFormatUtils.formatDuration(dur, f));
			}
		}
	}
	
}
