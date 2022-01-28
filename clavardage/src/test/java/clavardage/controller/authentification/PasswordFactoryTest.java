package clavardage.controller.authentification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordFactoryTest {

    private final String password = "test";

    @Test
    public void generateHashTest() {
        assertTrue(PasswordFactory.verify(password, PasswordFactory.generateHash(password)));
    }

    @Test
    public void verifyTest() {
        assertTrue(PasswordFactory.verify(password, PasswordFactory.generateHash(password)));
        assertTrue(PasswordFactory.verify("&é\"48\\'('", PasswordFactory.generateHash("&é\"48\\'('")));
        assertFalse(PasswordFactory.verify(password, PasswordFactory.generateHash("wrong")));
    }
}
