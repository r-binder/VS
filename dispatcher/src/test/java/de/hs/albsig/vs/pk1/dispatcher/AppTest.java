package de.hs.albsig.vs.pk1.dispatcher;

import java.util.Random;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {

            System.out.println(new Random().nextInt(2));
        }
    }
}
