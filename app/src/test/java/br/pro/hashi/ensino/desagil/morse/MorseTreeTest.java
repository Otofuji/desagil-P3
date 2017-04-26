package br.pro.hashi.ensino.desagil.morse;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class MorseTreeTest {
    private MorseTree tree;

    @Before
    public void setUp() {
        tree = new MorseTree();
    }

    @Test
    public void toA() {
        Assert.assertEquals('a', tree.translate(".-"));
    }

    @Test
    public void toB() {
        Assert.assertEquals('b', tree.translate("-..."));
    }
}
