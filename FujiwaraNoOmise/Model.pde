public class Model {
  public ArrayList<Person> people;
  public MyShop myshop;
  public FoeShop foes[];
  public Vogue vogue;
 
  public int frequency;
  public int voguefrequency;
  public int foecreationFreq;
  
  // intial = 0, game = 1
  public int state = 1;
  
  public Model (int frequency, int voguefrequency, int foecreationFreq, int exNum, int lvNum) {
    this.people = new ArrayList<Person> ();
    this.vogue = new Vogue((char)int(random(97, 103)));
    this.foes = new FoeShop[4];
    
    // this is temporary one
    Product p = new Product('a', 10, 0, 100);
    myshop = new MyShop("Ryu's department", p, 1000, exNum, lvNum);
    // till here
    
    createFoeShop();
    peopleinitialization (30);
    
    
    this.frequency = frequency;
    this.voguefrequency = voguefrequency;
    this.foecreationFreq = foecreationFreq;
  }
  
  // create n people
  public void peopleinitialization (int n) {
    for (int i = 0; i < 30; i++) {
      this.people.add(new Person(vogue.mostSold, foes, myshop));
    }
  }
  
  // create foe shop
  public void createFoeShop () {
    for (int i = 1; i < 5; i++) {
      this.foes[i - 1] = new FoeShop(("Shop " + i), 1000, i);
    }
  }

  public void calledOnUpdate () {
    if (frameCount % this.frequency == 0) {
      createPeople();
    }
    
    if (frameCount % this.voguefrequency == 0) {
      this.vogue.alterVogue();
    }
    
    if (frameCount % this.foecreationFreq == 0) {
      int r;
      for (int i = 0; i < this.foes.length; i++) {
        r = (int)random(100);
        this.foes[i].createRandomProduct(r);
      }
    }
    processPeople();
  }
  
  // character creation
  public void createMyShop (String name) {
    
  }
  
  // create people
  public void createPeople () {
    this.people.add(new Person(vogue.mostSold, foes, myshop));
  }
  
  public void processPeople () {
    for (int i = 0; i < people.size(); i++) {
      people.get(i).walkToward(myshop, foes);
      if (people.get(i).hasBought) {
        people.remove(i);
      }
    }
  }
}