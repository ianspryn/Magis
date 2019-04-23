package com.magis.app.login;

import org.junit.Assert;
import org.junit.Test;

public class PasswordTest {

    @Test
    public void emptyPassword() {
        Assert.assertFalse(Password.longEnough(""));
    }

    @Test
    public void tooShort() {
        Assert.assertFalse(Password.longEnough("1234567"));
    }

    @Test
    public void eightCharacters() {
        Assert.assertTrue(Password.longEnough("12345678"));
    }

    @Test
    public void longEnough() {
        Assert.assertTrue(Password.longEnough("1234567890"));
    }

    @Test
    public void noUpperCase() {
        Assert.assertFalse(Password.containsUpperCase("abcdef"));
    }

    @Test
    public void oneUpperCase() {
        Assert.assertTrue(Password.containsUpperCase("abCdef"));
    }

    @Test
    public void noLowerCase() {
        Assert.assertFalse(Password.containsLowerCase("ABCDEF"));
    }

    @Test
    public void oneLowerCase() {
        Assert.assertTrue(Password.containsLowerCase("ABcDEF"));
    }

    @Test
    public void noDigits() {
        Assert.assertFalse(Password.containsDigit("abcdef"));
    }

    @Test
    public void oneDigit() {
        Assert.assertTrue(Password.containsDigit("ab1def"));
    }

}