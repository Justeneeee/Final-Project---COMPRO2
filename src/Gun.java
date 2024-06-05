import java.util.Objects;

public class Gun {
    private String gunName;
    private int quantity;
    private int price;

    public Gun(String gunName, int quantity, int price) {
        this.gunName = gunName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getGunName() {
        return gunName;
    }

    public void setGunName(String gunName) {
        this.gunName = gunName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gun gun = (Gun) o;
        return Objects.equals(gunName, gun.gunName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gunName);
    }

}
