package inventory;

public class Inventory
{
    private int sauce;
    private int dough;
    private int cheese;
    private int sausage;
    private int pepperoni;
    private int bacon;
    private int pineapple;
    private int mushrooms;
    private int greenPeppers;
    private int onion;

    public Inventory() {}

    public int getSauce() {
        return sauce;
    }

    public void setSauce(int count) {
        this.sauce = count;
    }

    public int getDough() {
        return dough;
    }

    public void setDough(int count) {
        this.dough = count;
    }

    public int getCheese() {
        return cheese;
    }

    public void setCheese(int count) {
        this.cheese = count;
    }

    public int getSausage() {
        return sausage;
    }

    public void setSausage(int count) {
        this.sausage = count;
    }

    public int getPepperoni() {
        return pepperoni;
    }

    public void setPepperoni(int count) {
        this.pepperoni = count;
    }

    public int getBacon() {
        return bacon;
    }

    public void setBacon(int bacon) {
        this.bacon = bacon;
    }

    public int getPineapple() {
        return pineapple;
    }

    public void setPineapple(int count) {
        this.pineapple = count;
    }

    public int getMushrooms() {
        return mushrooms;
    }

    public void setMushrooms(int count) {
        this.mushrooms = count;
    }

    public int getGreenPeppers() { return greenPeppers; }

    public void setGreenPeppers(int count) {
        this.greenPeppers = count;
    }

    public int getOnion() {
        return onion;
    }

    public void setOnion(int count) {
        this.onion = count;
    }

    @Override
    public boolean equals(Object rhs)
    {
        if (rhs == null) {
            return false;
        }
        if (!Inventory.class.isAssignableFrom(rhs.getClass())) {
            return false;
        }

        final Inventory other = (Inventory) rhs;

        if ((this.sauce != other.sauce) ||
                (this.dough != other.dough) ||
                (this.cheese != other.cheese) ||
                (this.sausage != other.sausage) ||
                (this.pepperoni != other.pepperoni) ||
                (this.bacon != other.bacon) ||
                (this.pineapple != other.pineapple) ||
                (this.mushrooms != other.mushrooms) ||
                (this.greenPeppers != other.greenPeppers) ||
                (this.onion != other.onion)){
            return false;
        }

        return true;
    }
}
