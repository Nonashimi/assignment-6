package ex2;

public class Pizza {
    protected double price;

    public Pizza(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

class PepperoniTopping extends Pizza {
    public PepperoniTopping(Pizza pizza) {
        super(pizza.getPrice() + 2.0);
    }
}

class MushroomTopping extends Pizza {
    public MushroomTopping(Pizza pizza) {
        super(pizza.getPrice() + 1.5);
    }
}

class PizzaDecoratorExample {
    public static void main(String[] args) {
        Pizza basePizza = new Pizza(8.0);
        System.out.println("Цена базовой пиццы: $" + basePizza.getPrice());
        Pizza pepperoniPizza = new PepperoniTopping(basePizza);
        System.out.println("Цена пиццы с топпингом пепперони: $" + pepperoniPizza.getPrice());
        Pizza mushroomPizza = new MushroomTopping(basePizza);
        System.out.println("Цена пиццы с топпингом грибов: $" + mushroomPizza.getPrice());
        Pizza deluxePizza = new MushroomTopping(new PepperoniTopping(basePizza));
        System.out.println("Цена делюкс пиццы: $" + deluxePizza.getPrice());
    }
}
