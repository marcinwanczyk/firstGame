public class Weapon {
    //    atrybuty
    private int power;
    private int usage;
    private int maxUsage;

    //konstruktor
    public Weapon(int power, int maxUsage) {
        this.power = power;
        this.maxUsage = maxUsage;
        usage = this.maxUsage;
    }
    // get/set dla atrybutow

    public void breakOnHit() {
        usage -= 10;
    }

    public void repairWeapon() {
        usage = maxUsage;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public int getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(int maxUsage) {
        this.maxUsage = maxUsage;
    }
}