package org.generated.project.application;

import java.util.Random;

import org.seedstack.seed.Bind;
@Bind
public class RandomKeyGenerator {
	
	public int randomKey() {
		int key=0;
		Random random=new Random();
		while(key<10000) {
			key=random.nextInt(99999);
		}
		return key;
	}

}
