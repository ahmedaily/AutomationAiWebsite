import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class automateArts {

    public static void main(String[] args) throws IOException, CsvValidationException, InterruptedException {


        String csvFile = "src/main/resources/automatearts.csv";
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        String[] line;
        List<String> data = new ArrayList<>();

        while ((line = reader.readNext()) != null) {
            String rowData = String.join(",", line);
            data.add(rowData);
        }

        reader.close();

        // Create a new directory to save images
        File directory = new File("src/main/resources/images");
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Set Chrome options to download files automatically to the "images" folder
        String downloadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\images";
        System.out.println(downloadPath);

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", downloadPath);
        options.setExperimentalOption("prefs", prefs);

        // Start the Chrome browser with the options set
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Navigate to the webpage and wait for it to load
        driver.get("https://www.craiyon.com/");
        Thread.sleep(1000);

        // Loop over the data and generate images for each input
        for (int i = 1; i < data.size(); i++) {
            // Enter the input text and click the generate button
            driver.findElement(By.id("prompt")).sendKeys(Keys.CONTROL + "a");
            driver.findElement(By.id("prompt")).sendKeys(Keys.DELETE);
            driver.findElement(By.id("prompt")).sendKeys(data.get(i));
            driver.findElement(By.id("generateButton")).click();
            Thread.sleep(70000);

            // Click the third image in the generated images section
            driver.findElement(By.xpath("(//*[@class='grid grid-cols-3 gap-1 sm:gap-2']//parent::div)[3]"))
                    .click();
            Thread.sleep(10000);

            // Click the download button and wait for the file to download
            driver.findElement(By.xpath("//*[@alt='Download icon']"))
                    .click();
            Thread.sleep(10000);
        }

        // Quit the driver and exit
        driver.quit();
        System.exit(0);
    }
}
