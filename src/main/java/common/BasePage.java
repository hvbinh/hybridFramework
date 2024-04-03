package common;

import io.opentelemetry.api.GlobalOpenTelemetry;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BasePage {
    public void openPageUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    public String getCurrentPageUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public String getCurrentPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getCurrentPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    public void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void acceptAlert(WebDriver driver) {
        driver.switchTo().alert().accept();
    }

    public void cancelAlert(WebDriver driver) {
        driver.switchTo().alert().dismiss();
    }

    public String getTextlAlert(WebDriver driver) {
        return driver.switchTo().alert().getText();
    }

    public void setTextlAlert(WebDriver driver, String value) {
        driver.switchTo().alert().sendKeys(value);
    }

    public void waitAlertPresence(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.getGlobalConstants().getSHORT_TIME()));
        explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    public void switchToWindowById(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindow : allWindows) {
            if (!runWindow.equals(parentID)) {
                driver.switchTo().window(runWindow);
                break;
            }
        }

    }

    public boolean closeAllWindowsWithoutParent(WebDriver driver, String idParent) {
        Set<String> allWindows = driver.getWindowHandles();

        for (String runWindow : allWindows) {

            if (!runWindow.equals(idParent)) {
                driver.switchTo().window(runWindow);
                driver.close();
            }
        }
        driver.switchTo().window(idParent);
        if (driver.getWindowHandles().size() == 1)
            return true;
        else
            return false;
    }

    public void switchToWindowByTitle(WebDriver driver, String title) {
        Set<String> allWindows = driver.getWindowHandles();

        for (String runWindow : allWindows) {
            driver.switchTo().window(runWindow);
            String currentTitle = driver.getTitle();
            if (currentTitle.trim().equals(title)) {
                break;
            }
        }
    }

    public WebElement getElement(WebDriver driver, String locator) {
        return driver.findElement(getByXpath(locator));
    }

    public WebElement getElement(WebDriver driver, String locator, String... values) {
        return getElement(driver, getDynamicLocator(locator, values));
    }

    public List<WebElement> getElements(WebDriver driver, String locator) {
        return driver.findElements(getByXpath(locator));
    }

    public void clickToElement(WebDriver driver, String locator, String... values) {
        element = getElement(driver, getDynamicLocator(locator, values));

        if (driver.toString().toLowerCase().contains("internet explorer")) {
            clickToElementByJS(driver, locator, values);
            sleepInSecond(3);
        } else {
            element.click();
        }
    }

    public void clickToElement(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        if (driver.toString().toLowerCase().contains("internet explorer")) {
            clickToElementByJS(driver, locator);
            sleepInSecond(3);
        } else {
            element.click();
        }

    }

    public void sendkeyToElement(WebDriver driver, String locator, String value, String... values) {
        element = getElement(driver, getDynamicLocator(locator, values));
        element.clear();
        element.sendKeys(value);
    }

    public void sendkeyToElement(WebDriver driver, String locator, String value) {
        element = getElement(driver, locator);
        element.clear();
        element.sendKeys(value);
    }

    public By getByXpath(String locator) {
        return By.xpath(locator);
    }

    public String getDynamicLocator(String locator, String... values) {
        locator = String.format(locator, (Object[]) values);
        return locator;
    }

    public void selectItemInDropdown(WebDriver driver, String locator, String itemValue) {
        element = getElement(driver, locator);
        select = new Select(element);
        select.selectByVisibleText(itemValue);
    }

    public void selectItemInDropdown(WebDriver driver, String locator, String itemValue, String... values) {
        element = getElement(driver, getDynamicLocator(locator, values));
        select = new Select(element);
        select.selectByVisibleText(itemValue);
    }

    public String getFirstSelectedTextInDropDown(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }

    public boolean isDropdownMultiple(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        select = new Select(element);
        return select.isMultiple();
    }

    public void selectTheItemInCustomDropdown(WebDriver driver, String parentXpath, String childXpath, String expectedText) {

        getElement(driver, parentXpath).click();
        sleepInSecond(1);
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.getGlobalConstants().getSHORT_TIME()));
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childXpath)));

        elements = getElements(driver, childXpath);

        for (WebElement actualItem : elements) {
            if (actualItem.getText().trim().equals(expectedText)) {
                jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", actualItem);
                sleepInSecond(1);
                actualItem.click();
                sleepInSecond(1);
                break;
            }
        }

    }


    public void sleepInSecond(long second) {

        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public String getElementAttribute(WebDriver driver, String locator, String attributeName) {
        element = getElement(driver, locator);
        return element.getAttribute(attributeName);
    }

    public String getElementAttribute(WebDriver driver, String locator, String attributeName, String... values) {
        element = getElement(driver, getDynamicLocator(locator, values));
        return element.getAttribute(attributeName);
    }

    public String getElementText(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        return element.getText();
    }

    public String getElementText(WebDriver driver, String locator, String... values) {
        element = getElement(driver, locator, values);
        return element.getText();
    }

    public int countElementSize(WebDriver driver, String locator) {
        return getElements(driver, locator).size();
    }

    public int countElementSize(WebDriver driver, String locator, String... values) {
        return getElements(driver, getDynamicLocator(locator, values)).size();
    }

    public void checkToCheckbox(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void checkToCheckbox(WebDriver driver, String locator, String... values) {
        element = getElement(driver, getDynamicLocator(locator, values));
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void uncheckToCheckbox(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        if (element.isSelected()) {
            element.click();
        }
    }

    public boolean isElementDisplayed(WebDriver driver, String locator) {
        return getElement(driver, locator).isDisplayed();
    }

    public boolean isElementDisplayed(WebDriver driver, String locator, String... values) {
        try {
            return getElement(driver, getDynamicLocator(locator, values)).isDisplayed();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return false;
        }

    }

    public boolean isElementEnabled(WebDriver driver, String locator) {
        return getElement(driver, locator).isEnabled();
    }

    public boolean isElementSelected(WebDriver driver, String locator) {
        return getElement(driver, locator).isSelected();
    }

    public void switchToFrame(WebDriver driver, String locator) {
        driver.switchTo().frame(getElement(driver, locator));
    }

    public void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    public void doubleClickToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.doubleClick(getElement(driver, locator)).perform();
    }

    public void rightClickToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.contextClick(getElement(driver, locator)).perform();
    }

    public void hoverMouseToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.moveToElement(getElement(driver, locator)).perform();
    }

    public void hoverMouseToElement(WebDriver driver, String locator, String... values) {
        action = new Actions(driver);
        action.moveToElement(getElement(driver, getDynamicLocator(locator, values))).perform();
    }

    public void clickAndHoldToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.contextClick(getElement(driver, locator)).perform();
    }

    public void dragAndDropElement(WebDriver driver, String sourceLocator, String targetLocator) {
        action = new Actions(driver);
        action.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targetLocator)).perform();
    }

    public void sendKeyBoardToElement(WebDriver driver, String locator, Keys key) {
        action = new Actions(driver);
        action.sendKeys(getElement(driver, locator), key).perform();
    }

    public Object executeForBrowser(WebDriver driver, String javaScript) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript(javaScript);
    }

    public String getInnerText(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
    }

    public boolean isExpectedTextInInnerText(WebDriver driver, String textExpected) {
        jsExecutor = (JavascriptExecutor) driver;
        String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void navigateToUrlByJS(WebDriver driver, String url) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.location = '" + url + "'");
    }

    public void highlightElement(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        element = getElement(driver, locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public void clickToElementByJS(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        element = getElement(driver, locator);
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public void clickToElementByJS(WebDriver driver, String locator, String... values) {
        jsExecutor = (JavascriptExecutor) driver;
        element = getElement(driver, getDynamicLocator(locator, values));
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public void scrollToElement(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        element = getElement(driver, locator);
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        sleepInSecond(1);
    }

    public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
        jsExecutor = (JavascriptExecutor) driver;
        element = getElement(driver, locator);
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
    }

    public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
        jsExecutor = (JavascriptExecutor) driver;
        element = getElement(driver, locator);
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", element);
    }

    public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.getGlobalConstants().getSHORT_TIME()));
        jsExecutor = (JavascriptExecutor) driver;

        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }

    public boolean isImageLoaded(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        jsExecutor = (JavascriptExecutor) driver;

        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", element);
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    public void waitToElementVisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.getGlobalConstants().getSHORT_TIME()));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));

    }

    public void waitToElementVisible(WebDriver driver, String locator, String... values) {
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.getGlobalConstants().getSHORT_TIME()));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(locator, values))));
    }

    public void waitToElementInvisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.getGlobalConstants().getSHORT_TIME()));
        overrideImplicitWait(driver, GlobalConstants.getGlobalConstants().getSHORT_TIME());
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
        overrideImplicitWait(driver, GlobalConstants.getGlobalConstants().getLONG_TIME());
    }

    public void waitToElementInvisible(WebDriver driver, String locator, String... values) {
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.getGlobalConstants().getSHORT_TIME()));
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(getDynamicLocator(locator, values))));
    }

    public void waitToElementClickable(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.getGlobalConstants().getSHORT_TIME()));
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
    }

    public void waitToElementClickable(WebDriver driver, String locator, String... values) {
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.getGlobalConstants().getSHORT_TIME()));
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator, values))));
    }

    public boolean isElementUndisplayed(WebDriver driver, String locator) {
        overrideImplicitWait(driver, GlobalConstants.getGlobalConstants().getSHORT_TIME());
        elements = getElements(driver, locator);
        overrideImplicitWait(driver, GlobalConstants.getGlobalConstants().getLONG_TIME());
        if (elements.size() == 0) {
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }

    public void overrideImplicitWait(WebDriver driver, long timeSecond) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeSecond));
    }

    private WebDriverWait explicitWait;
    private WebElement element;
    private JavascriptExecutor jsExecutor;
    private Actions action;
    private Select select;
    private List<WebElement> elements;
}
