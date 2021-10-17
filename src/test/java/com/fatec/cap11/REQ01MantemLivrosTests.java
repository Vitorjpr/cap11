package com.fatec.cap11;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;
import org.junit.jupiter.api.Test;

class REQ01MantemLivrosTests {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;
	
	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver");
		driver = new ChromeDriver();
		driver.get("https://ts-scel-web.herokuapp.com/login");
		driver.manage().window().maximize();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
		
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		sleep();
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	void CT01CadastrarLivroComSucesso() {
		//Dado que o Livro Engenharia de Software não está cadastrado
		//Quando o usuário cadastrar o livro
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("3003");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// Então apresenta as informações do Livro cadastrado
		assertTrue(driver.getPageSource().contains("3003"));
	}
	
	@Test
	void CT02AlterarLivroComSucesso() {
		//Dado que o Livro Engenharia de Software está cadastrado
		//Quando o usuário editar o livro
		driver.findElement(By.linkText("Lista de Livros")).click();
		driver.findElement(By.cssSelector(".btn-sm:nth-child(1)")).click();
		// Então apresenta as informações do Livro cadastrado
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).clear();
		driver.findElement(By.id("autor")).sendKeys("Roger Pressman");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		
		//Então confirma que o valor foi alterado corretamente.
		assertTrue(driver.getPageSource().contains("Roger Pressman"));
	}
	
	@Test
	void CT03ExcluirLivroComSucesso() {
		//Dado que o Livro Engenharia de Software está cadastrado
		//Quando o usuário excluir o livro
		driver.findElement(By.linkText("Lista de Livros")).click();
		driver.findElement(By.cssSelector(".btn-sm:nth-child(2)")).click();
		//Então confirme que o livro não está mais na lista
		assertTrue(!driver.getPageSource().contains("3003"));
	}
	
	public String waitForWindow(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Set<String> whNow = driver.getWindowHandles();
		Set<String> whThen = (Set<String>) vars.get("window_handles");
		if (whNow.size() > whThen.size()) {
			whNow.removeAll(whThen);
		}
		return whNow.iterator().next();
	}

	public void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}