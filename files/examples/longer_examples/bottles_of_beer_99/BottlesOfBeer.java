package examples.longer_examples.bottles_of_beer_99;


public class BottlesOfBeer {
    public static void main(String[] args) {
        if(args.length == 0) {
            printBottles(99);
        } else {
            int n = Integer.parseInt(args[0]);
            printBottles(n);
        }

        System.out.println(bottlesOfBeer(10));
    }

    public static void printBottles(int bottleCount) {
        if(bottleCount <= 0) {
            System.out.println("No bottles - no song");
        }

        System.out.println("The \"" + bottlesOfBeer(bottleCount)+ "\" song");

        int bottle = bottleCount;

        while (bottle > 0) {
            String bottlesOfBeer = bottlesOfBeer(bottle);

            System.out.print(bottlesOfBeer + " on the wall, " + bottlesOfBeer + ".\nTake one down, pass it around, ");
            bottle--;
            System.out.println(bottlesOfBeer(bottle) + " on the wall.\n");
        }

        System.out.println("No more bottles of beer on the wall, no more bottles of beer.\n" +
                "Go to the store and buy some more, " + bottlesOfBeer(bottleCount) + " on the wall.");

    }

    public static String bottlesOfBeer(int count) {

        if(count == 0)
            return  "no more bottles of beer";
        else if(count == 1)
            return  "1 bottle  of beer";
        else
            return count + " bottles of beer";

    }
}
