public class GameManager {
  Model m;
  Graphic g;
  Controller c;
  
  public GameManager (int frequency, int voguefrequency, int foecreationFreq, int exNum, int lvNum) {
    m = new Model (frequency, voguefrequency, foecreationFreq, exNum, lvNum);
    g = new Graphic ();
    c = new Controller ();
  }
  
  public void updateThis() {
    if (m.state == 0) {
      g.drawInitialScreen("a");
    }
    else if (m.state == 1) {
      m.calledOnUpdate();
      g.drawThis(m.people, m.myshop, m.foes, c.howmany, c.selection, c.colorSelection);
    }
  }
  
  public void clickThis () {
    c.selectThis();
    if (c.selection < m.myshop.itemOnWindow.size()) {
      c.enterBehavior (true, m.myshop);
    }
    else if (c.selection < m.myshop.maxNum) {
      c.enterBehavior (false, m.myshop);
    }
  }
  
}