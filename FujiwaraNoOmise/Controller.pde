public class Controller {
  public int howmany = 0;
  public int selection = 0;
  public int colorSelection = 0;
  
  public Controller () {
  }
  
  public void selectThis () {
    setHowmany();
    selectionChange();
    setColorSelection();
  }
  
  public void setHowmany () {
    if (keyCode == UP) {
      howmany++;
    }
    else if (keyCode == DOWN) {
      if (howmany > 0) {
        howmany--;
      }
    }
    else if (keyCode == RIGHT) {
      howmany += 10;
    }
    else if (keyCode == LEFT) {
      if (howmany <= 10) {
        howmany = 0;
      }
      else {
        howmany -= 10;
      }
    }
  }
  
  public void selectionChange () {
    if (key == 'q') {
      selection = 0;
      howmany = 0;
      colorSelection = 0;
    }
    else if (key == 'w') {
      selection = 1;
      howmany = 0;
      colorSelection = 0;
    }
    else if (key == 'e') {
      selection = 2;
      howmany = 0;
      colorSelection = 0;
    }
    else if (key == 'a') {
      selection = 3;
      howmany = 0;
      colorSelection = 0;
    }
    else if (key == 's') {
      selection = 4;
      howmany = 0;
      colorSelection = 0;
    }
    else if (key == 'd') {
      selection = 5;
      howmany = 0;
      colorSelection = 0;
    }
  }
  
  public void setColorSelection () {
    if (key == '1') {
      colorSelection = 0;
    }
    else if (key == '2') {
      colorSelection = 1;
    }
    else if (key == '3') {
      colorSelection = 2;
    }
    else if (key == '4') {
      colorSelection = 3;
    }
    else if (key == '5') {
      colorSelection = 4;
    }
    else if (key == '6') {
      colorSelection = 5;
    }
  }
  
  public void enterBehavior (boolean isBuyingMode, MyShop ms) {
    if (keyCode == ENTER) {
      if (isBuyingMode) {
        ms.createSome(ms.itemOnWindow.get(selection), howmany);
      }
      else {
        ms.createProduct(howmany * 200, new Product ((char)(colorSelection + 97), howmany, 0, 100));
      }
    }
  }
}