package com.example.tdd.chap02;

import com.example.tdd.password.PasswordStrengh;
import com.example.tdd.password.PasswordStrenghMeter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordStrenghMeterTest {
    PasswordStrenghMeter meter = new PasswordStrenghMeter();

    private void assertStrength(String password, PasswordStrengh expStr) {
        PasswordStrengh result = meter.meter(password);
        assertEquals(expStr, result);
    }

    @Test
    void meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB", PasswordStrengh.STRONG);
        assertStrength("ab12!@Add", PasswordStrengh.STRONG);
    }

    @Test
    void meetsOtherCriteria_except_for_Length_Then_Normal() {
        assertStrength("ab12!@A", PasswordStrengh.NORMAL);
    }

    @Test
    void meetsOtherCriteria_except_for_number_Then_Normal() {
        assertStrength("ab!@ABqwer", PasswordStrengh.NORMAL);
    }

    @Test
    void nullInput_Then_Invalid() {
        assertStrength(null, PasswordStrengh.INVALID);
    }

    @Test
    void emptyInput_Then_Invalid() {
        assertStrength("", PasswordStrengh.INVALID);
    }

    @Test
    void mmetsOtherCriteria_except_for_Uppercase_Then_Normal() {
        assertStrength("ab12!@df", PasswordStrengh.NORMAL);
    }

    @Test
    void mmetsOnlyLengthCriteria_Then_Weak() {
        assertStrength("abdefghi", PasswordStrengh.WEAK);
    }

    @Test
    void meetsOnlyNumCriteria_Then_Weak() {
        assertStrength("12345", PasswordStrengh.WEAK);
    }

    @Test
    void meetsOnlyUpperCriteria_Then_Weak() {
        assertStrength("ABZEF", PasswordStrengh.WEAK);
    }

    @Test
    void meetsNoCriteria_Then_Weak() {
        assertStrength("abc", PasswordStrengh.WEAK);
    }
}
