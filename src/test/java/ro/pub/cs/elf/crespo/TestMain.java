package ro.pub.cs.elf.crespo;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class TestMain extends TestCase {

    @Test
    public void testPrint() {
        Assert.assertEquals("Hello World", new Main().print());
    }
}
