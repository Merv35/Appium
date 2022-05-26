import com.thoughtworks.gauge.Step;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class BaseStep extends BaseTest{
    FluentWait<WebDriver> wait;

    public BaseStep() {
        wait = new FluentWait<>(appiumDriver);
        wait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);
    }

    @Step("<second> kadar bekle")
    public void waitForsecond(int second) throws InterruptedException {
        Thread.sleep(1000*second);
    }
    @Step("<Key> id'li elemente tıkla")
    public void clickElementByid(String Key) throws InterruptedException {
        appiumDriver.findElement(By.id(Key)).click();
        waitForsecond(3);

    }
    @Step("<Key> id'li elemente <keyword> değerini yaz")
    public void sendKeyElementByid(String Key,String keyword) throws InterruptedException {
        appiumDriver.findElement(By.id(Key)).sendKeys(keyword);
        waitForsecond(3);
    }
    @Step("<Key> xpath'li elemente tıkla")
    public void clickElementByxpath(String Key) throws InterruptedException {
        appiumDriver.findElement(By.xpath(Key)).click();
        waitForsecond(3);

    }

    @Step("Sayfayı yukarı kaydır")
    public void swipeUpI(){
        Dimension dims = appiumDriver.manage().window().getSize();
        PointOption pointOptionStart, pointOptionEnd;
        int edgeBorder = 10;
        final int PRESS_TIME = 200; // ms
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
        pointOptionEnd = PointOption.point(dims.width / 2, edgeBorder);
        new TouchAction(appiumDriver)
                .press(pointOptionStart)
                // a bit more reliable when we add small wait
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                .moveTo(pointOptionEnd)
                .release().perform();
    }


    @Step("<id> ID'li element görünür mü kontrol et.")
    public void isElementVisibleByID(String id) throws InterruptedException {
        Assert.assertTrue(id + " ID'li element yok", isElementVisible(By.id(id)));
        //logger.info(id+" ID'li elementin görünür olduğu kontrol edildi.");
        waitForsecond(3);
    }
    @Step("<xpath> xpath'li element görünür mü kontrol et.")
    public void isElementVisibleByXpath(String xpath) throws InterruptedException {
        Assert.assertTrue(xpath + " xpath'li element yok", isElementVisible(By.xpath(xpath)));
        //logger.info(id+" ID'li elementin görünür olduğu kontrol edildi.");
        waitForsecond(3);
    }

    @Step("<xpath> xpath'li rastgele element seç.")
    public void randomItemPick(String xpath) throws InterruptedException {
        List<MobileElement> elementList = appiumDriver.findElements(By.xpath(xpath));
        Random random = new Random();
        int randomItemPicked = random.nextInt(elementList.size());
        elementList.get(randomItemPicked).click();
        //logger.info("Rastgele Bir element Seçildi.");
        waitForsecond(2);
    }

    @Step("<sayi> kez geriye git.")
    public void geriDon(int sayi) throws InterruptedException {

        for (int i = 0; i < sayi; i++) {
            appiumDriver.navigate().back();
    //        logger.info(i+" kere geri butonuna tıklandı.");
            waitForsecond(1);
        }

    }


    private boolean isElementVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}
