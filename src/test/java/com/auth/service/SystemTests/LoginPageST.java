package com.auth.service.SystemTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginPageST {

    WebDriver driver;

    @BeforeEach
    public void init() {
        driver = new ChromeDriver();
    }

    @Test
    public void loginSuccessTest() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/login_page.html");

        String email = "user@email.com";
        String password = "user123";

        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);

        WebElement button = driver.findElement(By.id("buttonLogin"));
        button.click();

        Thread.sleep(2000);

        WebElement loginMessage = driver.findElement(By.id("welcomeMessage"));

        Assertions.assertEquals("http://127.0.0.1:5500/dashboard_page.html", driver.getCurrentUrl());
        Assertions.assertEquals("Bem Vindo(a) " + email + "!", loginMessage.getText());
    }

    @Test
    public void loginInvalidPasswordTest() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/login_page.html");

        String email = "user@email.com";
        String password = "user122";

        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);

        WebElement button = driver.findElement(By.id("buttonLogin"));
        button.click();

        Thread.sleep(2000);

        WebElement loginMessage = driver.findElement(By.id("loginMsg"));

        Assertions.assertEquals("Invalid Password", loginMessage.getText());
    }

    @Test
    public void loginUserNotFoundTest() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/login_page.html");

        String email = "johndoe@email.com";
        String password = "john123";

        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);

        WebElement button = driver.findElement(By.id("buttonLogin"));
        button.click();

        Thread.sleep(2000);

        WebElement loginMessage = driver.findElement(By.id("loginMsg"));

        Assertions.assertEquals("User " + email + " not found", loginMessage.getText());
    }

}
