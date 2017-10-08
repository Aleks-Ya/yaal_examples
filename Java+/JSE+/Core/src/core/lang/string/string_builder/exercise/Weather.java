package core.lang.string.string_builder.exercise;

import org.junit.Test;

import static java.lang.System.out;

/**
 * Информация о температуре воздуха и о количестве осадков в течение 
 * месяца задана в виде двух одномерных массивов. Определить, сколько 
 * выпало осадков в виде снега и сколько - в виде дождя. 
 * (Для определенности предполагается, что при 0 градусов идет дождь). 
 */ 
public class Weather {
    @Test
    public void main() {
		final int[] t = new int[]{-1,   0,  3,  25, -16};
		final int[] p = new int[]{80, 116, 65, 125, 102};
		final int rainTemp = 0;
		
		if (t.length != p.length) {
			throw new IllegalStateException("Данные предоставлены не за все дни.");
		}
		
		int snow = 0;
		int rain = 0;
		
		for(int i = 0; i < t.length; i++) {
			if (t[i] >= rainTemp) {
				rain += p[i];
			} else {
				snow += p[i];
			}
		}
		
		out.println("Rain: " + rain);
		out.println("Snow: " + snow);
    }
}
