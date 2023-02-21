import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.By;
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

public class convertImages {

    public static void main(String[] args) throws IOException, CsvValidationException, InterruptedException {

        String csvFile = "src/main/resources/images_paths.csv";
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        String[] line;
        List<String> data = new ArrayList<>();

        while ((line = reader.readNext()) != null) {
            String rowData = String.join(",", line);
            data.add(rowData);
        }

        reader.close();

        File directory = new File("src/main/resources/images_jpg");
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Set Chrome options to download files automatically to the "images" folder
        String downloadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\images_jpg";
        System.out.println(downloadPath);

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", downloadPath);
        options.setExperimentalOption("prefs", prefs);


        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.navigate().to("https://www.iloveimg.com/convert-to-jpg/png-to-jpg");

        System.out.println(data.get(0));
        System.out.println(data.get(1));

        Thread.sleep(1000);
        for (int i = 0; i < data.size(); i++) {
            driver.findElement(By.xpath("//input[@type='file']")).sendKeys(data.get(i));
        }

        driver.findElement(By.xpath("//button[@id='processTask']")).click();
        Thread.sleep(25000);
        driver.findElement(By.xpath("//a[@id='pickfiles']")).click();

    }

}
