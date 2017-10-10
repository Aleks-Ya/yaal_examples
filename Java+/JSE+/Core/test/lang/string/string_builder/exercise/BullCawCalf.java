package lang.string.string_builder.exercise;

import org.junit.Test;

import static java.lang.System.out;


/**
 * Сколько можно купить быков, коров и телят, 
 * если плата за быка 10 рублей, за корову - 5 рублей, а телёнка - 0,5 рубля, 
 * если на 100 рублей надо купить 100 голов скота.
 */ 
public class BullCawCalf {
    @Test
    public void main() {
		final float bullPrice = 10f;
		final float cawPrice = 5f;
		final float calfPrice = 0.5f;
		final int headNeed = 100;
		final float sumMax = 100f;
		
		for (int bulls = 1; bulls < headNeed; bulls++) {
			for (int caws = 1; caws < headNeed; caws++) {
				for (int calfs = 1; calfs < headNeed; calfs++) {
					int heads = bulls + caws + calfs;
					if (heads == headNeed) {
						float sum = bulls * bullPrice + caws * cawPrice + calfs * calfPrice;
						if (sum <= sumMax) {
							out.printf("bulls=%d, caws=%d, calfs=%d, sum=%3.2f\n", bulls, caws, calfs, sum);
						}
					}
				}
			}
		}
    }
}
