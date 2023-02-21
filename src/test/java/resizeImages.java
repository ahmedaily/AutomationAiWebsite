import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class resizeImages {


    public static void main(String[] args) throws InterruptedException, IOException, CsvValidationException {

        String csvFile = "src/main/resources/jpg_images_paths.csv";
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        String[] line;
        List<String> data = new ArrayList<>();

        while ((line = reader.readNext()) != null) {
            String rowData = String.join(",", line);
            data.add(rowData);
        }

        reader.close();

        File directory = new File("src/main/resources/images_resized");
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Set Chrome options to download files automatically to the "images" folder
        String downloadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\images_resized";
        System.out.println(downloadPath);

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", downloadPath);
        options.setExperimentalOption("prefs", prefs);


        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        Thread.sleep(2000);
        driver.navigate().to("https://www.iloveimg.com/resize-image");


        Thread.sleep(1000);
        for (int i = 0; i < data.size(); i++) {
            driver.findElement(By.xpath("//input[@type='file']")).sendKeys(data.get(i));
        }
        Thread.sleep(2000);
        driver.findElement(By.id("tab-pixels")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//input[@name=\"pixels_width\"]")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("//input[@name=\"pixels_width\"]")).sendKeys(Keys.DELETE);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@name=\"pixels_width\"]")).sendKeys("3000");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@name=\"pixels_height\"]")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("//input[@name=\"pixels_height\"]")).sendKeys(Keys.DELETE);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@name=\"pixels_height\"]")).sendKeys("3000");
        Thread.sleep(3000);
        driver.findElement(By.id("processTask")).click();

        Thread.sleep(20000);
        driver.findElement(By.id("\"pickfiles\"")).click();

        driver.quit();

    }
}
