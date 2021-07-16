package commons;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    public static BasePage getBasePage() {
        return new BasePage();
    }
    public void openPageURL(WebDriver driver , String pageURL) {
        driver.get(pageURL);
    }


    public String getPageTitle(WebDriver driver){
        return driver.getTitle();

    }

    public String getPageURL(WebDriver driver){
        return driver.getCurrentUrl();
    }

    public String getPageSource(WebDriver driver){
        return driver.getPageSource();
    }

    public Alert waitAlertPresence(WebDriver driver) {
        explicitWait = new WebDriverWait(driver , timeout);
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert(WebDriver driver){
        alert = waitAlertPresence(driver);
        alert.accept();
    }

    public void cancelAlert(WebDriver driver){
        alert = waitAlertPresence(driver);
        alert.dismiss();
    }

    public void sendKeyToAlert(WebDriver driver , String value) {
        alert = waitAlertPresence(driver);
        alert.sendKeys(value);

    }

    public String getAlertText(WebDriver driver) {
        alert = driver.switchTo().alert();
        return alert.getText();

    }

    public void switchToWindowByID(WebDriver driver , String windowID) {
        Set<String> allWindowsID = driver.getWindowHandles();

        for (String id : allWindowsID) {
            if (!id.equals(windowID)) {
                driver.switchTo().window(id);
                break;
            }
        }
    }

    public void switchToWindowByTitle(WebDriver driver,String tabtitle) {
        Set<String> AllWindowID = driver.getWindowHandles();

        for (String id : AllWindowID) {
            driver.switchTo().window(id);
            String ActualTitle = driver.getTitle();
            if (ActualTitle.equals(tabtitle)) {
                break;
            }
        }
    }

    public void CloseAllWindowExceptParent(WebDriver driver,String parentID){
        Set<String> AllWindowID = driver.getWindowHandles();

        for (String id : AllWindowID) {
            if (!id.equals(parentID)) {
                driver.switchTo().window(id);
                driver.close();
            }
        }
    }

    public void sleepInSecond(long timeoutInSecond) {
        try {
            Thread.sleep(timeoutInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public By getByXpath(String locator) {
        return By.xpath(locator);
    }

    public WebElement getElement(WebDriver driver , String locator ) {
        return driver.findElement(getByXpath(locator));
    }

    public List<WebElement> getElements(WebDriver driver , String locator){
        return driver.findElements(getByXpath(locator));
    }

    public void clickToElement(WebDriver driver , String locator) {
        getElement(driver , locator).click();
    }

    public void sendKeyToElement(WebDriver driver , String locator , String value) {
        getElement(driver , locator).clear();
        getElement(driver , locator).sendKeys(value);;
    }

    public int getElementsize(WebDriver driver, String locator) {
        return getElements(driver , locator).size();
    }

    public void selectDropDownByText(WebDriver driver , String locator , String Text) {
        select = new Select(getElement(driver , locator));
        select.selectByVisibleText(Text);
    }

    public String getSelectedItemFromDropDown(WebDriver driver , String locator) {
        select = new Select(getElement(driver , locator));
        return select.getFirstSelectedOption().getText();

    }

    public boolean isDropDownMultiPle(WebDriver driver , String locator) {
        select = new Select(getElement(driver , locator));
        return select.isMultiple();
    }

    public void selectItemInCustomDropdown(WebDriver driver , String parentLocator, String childLocator, String expectedItem) {
        getElement(driver , parentLocator).click();
        sleepInSecond(1);
        explicitWait = new WebDriverWait(driver , timeout);

        List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childLocator)));

        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedItem)) {
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childLocator);
                sleepInSecond(1);

                item.click();
                sleepInSecond(1);
            }
        }
    }

    public void selectMultiItemInDropdown(WebDriver driver , String parentLocator, String childLocator, String[] expectedValueItem) {
        getElement(driver, parentLocator).click();
        sleepInSecond(1);

        explicitWait = new WebDriverWait(driver , timeout);
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childLocator)));

        for (WebElement childElement : allItems) {
            for (String item : expectedValueItem) {
                if (childElement.getText().trim().equals(item)) {
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
                    sleepInSecond(1);

                    childElement.click();
                    sleepInSecond(1);
                }
            }
        }
    }

    public String getAttributeValue(WebDriver driver , String locator , String attributeName){
        return getElement(driver , locator).getAttribute(attributeName);
    }

    public String getElementText(WebDriver driver , String locator){
        return getElement(driver , locator).getText();
    }

    public void checktoCheckBoxOrRadio(WebDriver driver , String locator){
        if (!isElementSelected(driver , locator)) {
            getElement(driver , locator).click();
        }
    }

    public void unCheckCheckBox(WebDriver driver , String locator){
        if (isElementSelected(driver,locator)){
            getElement(driver , locator).click();
        }
    }

    public boolean isElementDisplayed(WebDriver driver , String locator){
        return getElement(driver,locator).isDisplayed();
    }

    public boolean isElementEnable(WebDriver driver , String locator){
        return getElement(driver,locator).isEnabled();
    }

    public boolean isElementSelected(WebDriver driver , String locator){
        return getElement(driver,locator).isSelected();
    }

    public WebDriver switchToFrameByElement(WebDriver driver , String locator){
        return driver.switchTo().frame(getElement(driver , locator));
    }

    public WebDriver switchToDefaultContent(WebDriver driver){
        return driver.switchTo().defaultContent();
    }

    public void hoverElement(WebDriver driver, String locator){
        action = new Actions(driver);
        action.moveToElement(getElement(driver , locator)).perform();
    }

    public void doubleClick(WebDriver driver, String locator){
        action = new Actions(driver);
        action.doubleClick(getElement(driver , locator)).perform();
    }

    public void rightClick(WebDriver driver, String locator){
        action = new Actions(driver);
        action.contextClick(getElement(driver, locator)).perform();
    }

    public void DragAndDrop(WebDriver driver, String sourcelocator , String targetLocator){
        action = new Actions(driver);
        action.dragAndDrop(getElement(driver , sourcelocator) , getElement(driver , targetLocator)).perform();

    }

    public void pressKeyToElement(WebDriver driver , String locator , Keys key){
        action = new Actions(driver);
        action.sendKeys(getElement(driver, locator) , key).perform();
    }

    public Object executeForBrowser(WebDriver driver,String javaScript) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript(javaScript);
    }

    public String getInnerText(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
    }

    public boolean areExpectedTextInInnerText(WebDriver driver,String textExpected) {
        jsExecutor = (JavascriptExecutor) driver;
        String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void navigateToUrlByJS(WebDriver driver,String url) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.location = '" + url + "'");
    }

    public void highlightElement(WebDriver driver,String locator) {
        jsExecutor = (JavascriptExecutor) driver;

        WebElement element = getElement(driver,locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public void clickToElementByJS(WebDriver driver,String locator) {
        jsExecutor = (JavascriptExecutor) driver;

        jsExecutor.executeScript("arguments[0].click();", getElement(driver,locator));
    }

    public void scrollToElement(WebDriver driver,String locator) {
        jsExecutor = (JavascriptExecutor) driver;

        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver,locator));
    }

    public void sendkeyToElementByJS(WebDriver driver,String locator, String value) {
        jsExecutor = (JavascriptExecutor) driver;

        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(driver,locator));
    }

    public void removeAttributeInDOM(WebDriver driver,String locator, String attributeRemove) {
        jsExecutor = (JavascriptExecutor) driver;

        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(driver,locator));
    }

    public boolean areJqueryandJSLoadSuccess(WebDriver driver){
        explicitWait = new WebDriverWait(driver , timeout);
        jsExecutor = (JavascriptExecutor) driver;

        ExpectedCondition<Boolean> jQueryload = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver){
                try {
                    return ((long) jsExecutor.executeScript("return jQuery.active") == 0);
                }catch (Exception e){
                    return true;
                }
            }
        };
        ExpectedCondition<Boolean> JSLoad = new ExpectedCondition<Boolean>(){
            @Override
            public Boolean apply(WebDriver driver){
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        return explicitWait.until(jQueryload) && explicitWait.until(JSLoad);

    }

    public String getElementValidationMessage(WebDriver driver,String locator) {
        jsExecutor = (JavascriptExecutor) driver;

        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(driver,locator));
    }

    public boolean isImageLoaded(WebDriver driver,String locator) {
        jsExecutor = (JavascriptExecutor) driver;

        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getElement(driver,locator));
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    public void waitElementVisible(WebDriver driver , String locator){
        explicitWait = new WebDriverWait(driver , timeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));

    }

    public void waitAllElementVisible(WebDriver driver , String locator){
        explicitWait = new WebDriverWait(driver , timeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(locator)));

    }

    public void waitElementClickable(WebDriver driver , String locator){
        explicitWait = new WebDriverWait(driver , timeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
    }

    public void waitElementInvisible(WebDriver driver , String locator){
        explicitWait = new WebDriverWait(driver , timeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));

    }
    public int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(9999);
    }

    //Check



    private Alert alert;
    private WebDriverWait explicitWait;
    private long timeout = 15;
    private Select select;
    private JavascriptExecutor jsExecutor;
    private Actions action;






}

