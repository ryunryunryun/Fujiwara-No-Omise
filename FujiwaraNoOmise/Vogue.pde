public class Vogue {
  public char mostSold;
  
  // contructor
  public Vogue (char mostSold) {
    this.mostSold = mostSold;
  }
  
  // todo
  public void alterVogue () {
    int rand = (int)random (6);
    if (rand == 0) {
      mostSold = 'a';
    }
    else if (rand == 1) {
      mostSold = 'b';
    }
    else if (rand == 2) {
      mostSold = 'c';
    }
    else if (rand == 3) {
      mostSold = 'd';
    }
    else if (rand == 4) {
      mostSold = 'e';
    }
    else if (rand == 5) {
      mostSold = 'f';
    }
  }
}