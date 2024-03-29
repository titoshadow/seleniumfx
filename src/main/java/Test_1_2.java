import main.Main;
import main.SeleniumDAO;
import main.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;


// Create a group called GruporcNverXYZ and add the agents that have been created before

public class Test_1_2 {
    public static void main(String[] args) {

        // Go to http://pruebas7.dialcata.com/dialapplet-web/
        WebDriver driver = SeleniumDAO.initializeFirefoxDriver();
        driver.get("http://pruebas7.dialcata.com/dialapplet-web/");

        Main.loginDialappletWeb("admin", "admin", driver);
        // Click on Admin tab
        WebElement adminTab = SeleniumDAO.selectElementBy("id","ADMIN", driver);
        SeleniumDAO.click(adminTab);
        // Click on "Users" left menu
        WebElement users = SeleniumDAO.selectElementBy("xpath", "/html/body/div[2]/div[1]/h3[2]",driver);
        SeleniumDAO.click(users);
        // Click on Modify agent groups button in left menu
        WebElement modAgentsGroups = SeleniumDAO.selectElementBy("xpath", "/html/body/div[2]/div[1]/div[2]/div/div[2]/p/a", driver);
        SeleniumDAO.click(modAgentsGroups);

        WebDriverWait waiting = new WebDriverWait(driver, 20);

        waiting.until(ExpectedConditions.presenceOfElementLocated(By.id("contenido")));
        WebElement containerTableFirstSearch = SeleniumDAO.selectElementBy("id", "contenido",driver);
        WebElement tableFirstSearch = SeleniumDAO.selectElementBy("className", "tabla-principal", containerTableFirstSearch);

        List<WebElement> listOfRows = tableFirstSearch.findElements(By.tagName("tr"));
        int rows = listOfRows.size();
        int[] ids = new int[rows];
        for(int i = 1; i<rows-1; i++){
            WebElement currentId = SeleniumDAO.selectElementBy("xpath", "/html/body/div[2]/div[3]/div[2]/div[3]/div/table/tbody/tr["+i+"]/td[1]", driver);
            ids[i] = Integer.parseInt(currentId.getText());
        }
        int currentMax = Arrays.stream(ids).max().getAsInt();

        String name = "GruporcNver7816";
        Utils.generateUniqueID(name);

        //name = name.concat(uniqueID);

        WebElement groupName = SeleniumDAO.selectElementBy("id","new_groupname",driver);
        groupName.sendKeys(name);
        // Click on add button
        WebElement newGroup = SeleniumDAO.selectElementBy("cssSelector", "img[src='imagenes/add2.png']",driver);
        SeleniumDAO.click(newGroup);
        // Taking ID of new Group
        SeleniumDAO.switchToDefaultContent(driver);
        waiting.until(ExpectedConditions.presenceOfElementLocated(By.id("contenido")));

        WebElement containerTableSecondSearch = SeleniumDAO.selectElementBy("id", "contenido",driver);
        WebElement tableSecondSearch = SeleniumDAO.selectElementBy("className","tabla-principal", containerTableSecondSearch);
        List<WebElement> listOfRows2 = tableSecondSearch.findElements(By.tagName("tr"));
        int rows2 = listOfRows2.size();
        int[] ids2 = new int[rows2];
        for(int i = 1; i<rows2-1; i++){
            WebElement currentId = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/div[3]/div/table/tbody/tr["+i+"]/td[1]"));
            ids2[i] = Integer.parseInt(currentId.getText());
        }
        int newMax = Arrays.stream(ids2).max().getAsInt();
        // Transfer users to new group
        WebElement gestUsers = SeleniumDAO.selectElementBy("xpath","//*[@id=\"users-"+newMax+"\"]",driver);
        SeleniumDAO.click(gestUsers);
        // Waiting to load fancy-box and change driver to fancy-box
        SeleniumDAO.switchToFrame("fancybox-frame", driver);
        // Take left column(users column)
        WebElement leftColumn = SeleniumDAO.selectElementBy("id", "leftList",driver);
        // Select a Agent
        WebElement agent = SeleniumDAO.selectElementBy("xpath","/html/body/form/div/div[1]/div/div[1]/div/ul/li[4]",leftColumn);
        // Take right column(group users)
        WebElement groupSpace =  SeleniumDAO.selectElementBy("xpath", "//*[@id=\"rightList\"]",driver);
        // Make the drag and drop action
        //Actions moveAgent = new Actions(driver);
        //moveAgent.dragAndDrop(agent,groupSpace).build().perform();


        // Select a Agent
        WebElement agent2 = SeleniumDAO.selectElementBy("xpath","/html/body/form/div/div[1]/div/div[1]/div/ul/li[2]",leftColumn);
        // Take right column(group users)
        WebElement groupSpace2 =  SeleniumDAO.selectElementBy("xpath", "//*[@id=\"rightList\"]",driver);

        SeleniumDAO.dragAndDropAction(agent,groupSpace,driver);
        System.out.println("Mesperooo");
        //driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ya !");
        SeleniumDAO.dragAndDropAction(agent2,groupSpace2,driver);
        // Click on send button to finish the modification
        //WebElement send = SeleniumDAO.selectElementBy("xpath","/html/body/form/div/p[3]/input",driver);
        //SeleniumDAO.click(send);

        // Checking that the test works correctly
        // Take all ids of the agent groups of tabla principal and find the max value,
        // this value is the id of our new AgentGroup

        if(currentMax < newMax) System.out.println("Prueba creación grupo agentes finalizada con éxito !\nGenerado grupo con ID: "+newMax);
        else System.out.println("Algo ha petado, repasar");

        //driver.close();
    }
}
