package com.mastercard.developer.utils;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.NoSuchFileException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;

import static org.hamcrest.CoreMatchers.isA;

public class EncryptionUtilsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String EXPECTED_ENCODED_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQD0ynqAQWn0T7/VJLletTJgoxsTt5TR3IkJ+Yk/Pxg6Q5hXuiGrBdC+OVo/9hrNnptuZh9rZYKto6lbSjYFiKMeBDvPZrYDPzusp0C0KllIoVbzYiOezD76XHsQAEje0UXbzZlXstPXef2bi2HkqV26ST167L5O4moK8+7jHMT80T6XgsUyvyt8PjsQ9CSu6fnD9NfCSYmt2cb16OXcEtA7To2zoGznXqB6JhntFjG0jxee7RkLR+moOqMI9kFM5GSIV4uhwQ9FtOCjUf7TFAU12wwfX/QXUEj6G93GVtzf6QdkVkWh4EyRHeMLyMNc5c0Iw1ZvXdOKfoeo9F47QpbzAgMBAAECggEAK3dMmzuCSdxjTsCPnc6E3H35z914Mm97ceb6RN26OpZIFcO6OLj2oOBkMxlLFxnDta2yhIpo0tZNuyUJRKBHfov35tLxHNB8kyK7rYIbincDjoHtm0PfJuuG+odiaRY11lrCkLzzOr6xlo4AWu7r8qkQnqQtAqrXc4xu7artG4rfMIunGnjjWQGzovtey1JgZctO97MU4Wvw18vgYBI6JM4eHJkZxgEhVQblBTKZs4OfiWk6MRHchgvqnWugwl213FgCzwy9cnyxTP13i9QKaFzL29TYmmN6bRWBH95z41M8IAa0CGahrSJjudZCFwsFh413YWv/pdqdkKHg1sqseQKBgQD641RYQkMn4G9vOiwB/is5M0OAhhUdWH1QtB8vvhY5ISTjFMqgIIVQvGmqDDk8QqFMOfFFqLtnArGn8HrKmBXMpRigS4ae/QgHEz34/RFjNDQ9zxIf/yoCRH5PmnPPU6x8j3bj/vJMRQA6/yngoca+9qvi3R32AtC5DUELnwyzNwKBgQD5x1iEV+albyCNNyLoT/f6LSH1NVcO+0IOvIaAVMtfy+hEEXz7izv3/AgcogVZzRARSK0qsQ+4WQN6Q2WG5cQYSyB92PR+VgwhnagVvA+QHNDL988xoMhB5r2D2IVSRuTB2EOg7LiWHUHIExaxVkbADODDj7YV2aQCJVv0gbDQJQKBgQCaABix5Fqci6NbPvXsczvM7K6uoZ8sWDjz5NyPzbqObs3ZpdWK3Ot4V270tnQbjTq9M4PqIlyGKp0qXO7ClQAskdq/6hxEU0UuMp2DzLNzlYPLvON/SH1czvZJnqEfzli+TMHJyaCpOGGf1Si7fhIk/f0cUGYnsCq2rHAU1hhRmQKBgE/BJTRs1MqyJxSwLEc9cZLCYntnYrr342nNLK1BZgbalvlVFDFFjgpqwTRTT54S6jR6nkBpdPmKAqBBcOOX7ftL0b4dTkQguZLqQkdeWyHK8aiPIetYyVixkoXM1xUkadqzcTSrIW1dPiniXnaVc9XSxtnqw1tKuSGuSCRUXN65AoGBAN/AmT1S4PAQpSWufC8NUJey8S0bURUNNjd52MQ7pWzGq2QC00+dBLkTPj3KOGYpXw9ScZPbxOthBFzHOxERWo16AFw3OeRtn4VB1QJ9XvoA/oz4lEhJKbwUfuFGGvSpYvg3vZcOHF2zlvcUu7C0ub/WhOjV9jZvU5B2Ev8x1neb";

    @Test
    public void testLoadEncryptionCertificate_ShouldSupportPem() throws Exception {

        // GIVEN
        String certificatePath = "./src/test/resources/test_certificate.pem";

        // WHEN
        Certificate certificate = EncryptionUtils.loadEncryptionCertificate(certificatePath);

        // THEN
        Assert.assertNotNull(certificate);
        Assert.assertEquals("X.509", certificate.getType());
    }

    @Test
    public void testLoadEncryptionCertificate_ShouldSupportDer() throws Exception {

        // GIVEN
        String certificatePath = "./src/test/resources/test_certificate.der";

        // WHEN
        Certificate certificate = EncryptionUtils.loadEncryptionCertificate(certificatePath);

        // THEN
        Assert.assertNotNull(certificate);
        Assert.assertEquals("X.509", certificate.getType());
    }

    @Test
    public void testLoadDecryptionKey_ShouldSupportPkcs8Der() throws Exception {

        // GIVEN
        String keyPath = "./src/test/resources/test_key_pkcs8.der";

        // WHEN
        PrivateKey privateKey = EncryptionUtils.loadDecryptionKey(keyPath);

        // THEN
        Assert.assertNotNull(privateKey);
        Assert.assertEquals("RSA", privateKey.getAlgorithm());
        Assert.assertArrayEquals(Base64.decodeBase64(EXPECTED_ENCODED_KEY), privateKey.getEncoded());
    }

    @Test
    public void testLoadDecryptionKey_ShouldSupportPkcs8Base64Pem() throws Exception {

        // GIVEN
        String keyPath = "./src/test/resources/test_key_pkcs8.pem";

        // WHEN
        PrivateKey privateKey = EncryptionUtils.loadDecryptionKey(keyPath);

        // THEN
        Assert.assertNotNull(privateKey);
        Assert.assertEquals("RSA", privateKey.getAlgorithm());
        Assert.assertArrayEquals(Base64.decodeBase64(EXPECTED_ENCODED_KEY), privateKey.getEncoded());
    }

    @Test
    public void testLoadDecryptionKey_ShouldSupportPkcs1Base64Pem_Nominal() throws Exception {

        // GIVEN
        String keyPath = "./src/test/resources/test_key_pkcs1.pem";

        // WHEN
        PrivateKey privateKey = EncryptionUtils.loadDecryptionKey(keyPath);

        // THEN
        Assert.assertNotNull(privateKey);
        Assert.assertEquals("RSA", privateKey.getAlgorithm());
        Assert.assertArrayEquals(Base64.decodeBase64(EXPECTED_ENCODED_KEY), privateKey.getEncoded());
    }

    @Test
    public void testLoadDecryptionKey_ShouldSupportPkcs1Base64Pem_1024bits() throws Exception {

        // GIVEN
        String keyPath = "./src/test/resources/test_key_pkcs1-1024.pem";

        // WHEN
        PrivateKey privateKey = EncryptionUtils.loadDecryptionKey(keyPath);

        // THEN
        Assert.assertNotNull(privateKey);
        Assert.assertEquals("RSA", privateKey.getAlgorithm());
    }

    @Test
    public void testLoadDecryptionKey_ShouldSupportPkcs1Base64Pem_4096bits() throws Exception {

        // GIVEN
        String keyPath = "./src/test/resources/test_key_pkcs1-4096.pem";

        // WHEN
        PrivateKey privateKey = EncryptionUtils.loadDecryptionKey(keyPath);

        // THEN
        Assert.assertNotNull(privateKey);
        Assert.assertEquals("RSA", privateKey.getAlgorithm());
    }

    @Test
    public void testLoadDecryptionKey_ShouldSupportPkcs12() throws Exception {

        // GIVEN
        String keyContainerPath = "./src/test/resources/test_key.p12";
        String keyAlias = "mykeyalias";
        String keyPassword = "Password1";

        // WHEN
        PrivateKey privateKey = EncryptionUtils.loadDecryptionKey(keyContainerPath, keyAlias, keyPassword);

        // THEN
        Assert.assertNotNull(privateKey.getEncoded());
        Assert.assertEquals("RSA", privateKey.getAlgorithm());
        String expectedBase64Key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCYoc5Ue4MKxHIQeSESKQiIv341EFDtfAlAsXP74modJuwnSLOfSkFNgKH4y6vSKiUK7BxU2KFy7FkRJ9/vceJmP9MD6bWPgT2Wg4iSQxgPtAHEVps9MYvkhW0lt0hyhAcGLUR3kb4YjSkGfa8EzG/G2g+/VKdL0mnSgWhCnSBnR0xRwWccgdRTLm20/jzXkmHD92DBR7kDgiBUrPWTfLHDnsVoIUut6BAPI83TIjHjVG1Jn8K0prbGeQU9ALwaL36qvppYpmCqaAGHOM2fXsEPFNhEZxQpbyW2M4PtXHnjSqlNOKN2tmdF3jWwm9hKZ9xeaWJkBmBnLe3tNz0OdO0pAgMBAAECggEBAJHQGn5JFJJnw5SLM5XWz4lcb2SgNr/5/BjqriQXVEqPUZHh+X+Wf7ZbyeEWKgp4KrU5hYNlBS/2LMyf7GYixSfrl1qoncP/suektwcLw+PUks+P8XRPbhadhP1AEJ0eFlvHSR51hEaOLIA/98C80ZgF4H9njv93f5MT/5eL5lXipFX1dcxUB55q9QOtQ7uCg++NyG5F6u4FxbNtOtsjyNzWZSjYsjSyGHDip9ScDOPNsGQfznxo/oifdXvc25BgWvRflIIYEP08eeUSuGW2nUnx+Joc0oZTkC0wfU+aqKlaZp8zfOEIm0gUDgWtgnq5I5JHJMuW6BtA4K3E+nyP0lECgYEAzIbNx/lVxmFPbPp+AG9LD3JLycjdmTzwpHK44MsaUBOZ9PkLZs0NpR5z0/qcFb8YGGz3qN6E/TTydmfXCpZ3bxP3+x81gL9SVG/y2GP/ky/REA0jFycwVlONeVnd09xPNNLZLUgZhWyAQIA2pmVMh8W+pX6ojxGgOe+KIGutJCUCgYEAvwuNciTzkjBz9nFCjLONvP05WMdIAXo1uxd17iQ0lhRtmHbphojFAPcHYocm2oUXJo5nLvy+u8xnxbyXaZHmRqm98AzmBTtpphFtgfTtv/cSvOsBpdyyaJaN12IUs2XYACGBRa2DUkgxxvHtbmjFGFIU+5VgjOG8g0LfoPhLM7UCgYAmdRaOioihY7zOjg9RP5wKjIBJsfZREQ9irJus0SPieL0TPhzxuI7fRGmdK1tcD3GVbi/nVegFwIXy07WwrPhKL6QKWSTzT4ZIkEBGhg8RewVBkmbNvLWvFcjdT5ORebR/B0KE7DC4UN2Qw0sDYLrSMNGXRsilFjhdjHgZfoWw7QKBgAZrQvNk3nI5AoxzPcMwfUCuWXDsMTUrgAarQSEhQksQoKYQyMPmcIgZxLvAwsNw2VhITJs9jsMMmSgBsCyx5ETXizQ3mrruRhx4VW+aZSqgCJckZkfGZJAzDsz/1KY6c8l9VrSaoeDv4AxJMKsXBhhNGbtiR340T3sxkgX8kbpJAoGBAII2aFeQ4oE8DhSZZo2bpJxO072xy1P9PRlyasYBJ2sNiF0TTguXJB1Ncu0TM0+FLZXIFddalPgv1hY98vNX22dZWKvD3xJ7HRUx/Hyk+VEkH11lsLZ/8AhcwZAr76cE/HLz1XtkKKBCnnlOLPZN03j+WKU3p1fzeWqfW4nyCALQ";
        Assert.assertArrayEquals(Base64.decodeBase64(expectedBase64Key), privateKey.getEncoded());
    }

    @Test
    public void testLoadDecryptionKey_ShouldThrowIllegalArgumentException_WhenInvalidKey() throws Exception {

        // GIVEN
        String keyPath = "./src/test/resources/test_invalid_key.der";

        // THEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Unexpected key format!");
        expectedException.expectCause(isA(InvalidKeySpecException.class));

        // WHEN
        EncryptionUtils.loadDecryptionKey(keyPath);
    }

    @Test
    public void testLoadDecryptionKey_ShouldThrowNoSuchFileException_WhenKeyFileDoesNotExist() throws Exception {

        // GIVEN
        String keyPath = "./src/test/resources/some_file";

        // THEN
        expectedException.expect(NoSuchFileException.class);

        // WHEN
        EncryptionUtils.loadDecryptionKey(keyPath);
    }
}
