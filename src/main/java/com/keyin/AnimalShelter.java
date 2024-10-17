import java.util.LinkedList;
import java.util.Queue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

abstract class Animal {
    private String name;
    private String breed;
    private int age;
    private String sex;
    private LocalDate intakeDate;

    public Animal(String name, String breed, int age, String sex, LocalDate intakeDate) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.sex = sex;
        this.intakeDate = intakeDate;
    }

    // Getters
    public LocalDate getIntakeDate() {
        return intakeDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("%s (%s %s, Breed: %s, Age: %d, Intake Date: %s)",
                name, sex, getClass().getSimpleName(), breed, age, intakeDate.format(formatter));
    }

    public String generateAdoptionMessage(String ownerName, String contactInfo) {
        LocalDate adoptionDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String animalType = this instanceof Dog ? "dog" : "cat";

        return String.format("""
            🎉 Congratulations on your new family member, %s! 🎉
            
            You've adopted %s, a wonderful %s %s.
            
            Adoption Details:
            - Name: %s
            - Type: %s
            - Breed: %s
            - Age: %d years old
            - Sex: %s
            - Intake Date: %s
            - Adoption Date: %s
            - Contact Info: %s
            
            Thank you for choosing to adopt and giving %s a forever home!
            
            Best wishes,
            The Keyin Animal Adoption Center Team
            """,
                ownerName, name, sex, animalType, name, animalType, breed, age, sex,
                intakeDate.format(formatter), adoptionDate.format(formatter), contactInfo, name);
    }
}

class Dog extends Animal {
    public Dog(String name, String breed, int age, String sex, LocalDate intakeDate) {
        super(name, breed, age, sex, intakeDate);
    }
}

class Cat extends Animal {
    public Cat(String name, String breed, int age, String sex, LocalDate intakeDate) {
        super(name, breed, age, sex, intakeDate);
    }
}

public class AnimalShelter {
    private Queue<Dog> dogs;
    private Queue<Cat> cats;

    public AnimalShelter() {
        dogs = new LinkedList<>();
        cats = new LinkedList<>();
    }

    // Enqueue method to add animals to their respective queues
    public void enqueue(Animal animal) {
        if (animal instanceof Dog) {
            dogs.add((Dog) animal);
        } else {
            cats.add((Cat) animal);
        }
    }

    // Find and remove the oldest animal (dog or cat) based on intake date (FIFO)
    public Animal dequeueAny() {
        if (dogs.isEmpty() && cats.isEmpty()) {
            return null; // No animals in the shelter
        } else if (dogs.isEmpty()) {
            return dequeueOldestCat(); // If no dogs, adopt the oldest cat
        } else if (cats.isEmpty()) {
            return dequeueOldestDog(); // If no cats, adopt the oldest dog
        } else {
            // Compare the intake dates of the oldest dog and the oldest cat
            Dog oldestDog = findOldestDog();
            Cat oldestCat = findOldestCat();
            if (oldestDog.getIntakeDate().isBefore(oldestCat.getIntakeDate())) {
                return dequeueOldestDog(); // Dog has been in the shelter longer
            } else {
                return dequeueOldestCat(); // Cat has been in the shelter longer
            }
        }
    }

    // Find and remove the oldest dog based on intake date (FIFO)
    public Dog dequeueOldestDog() {
        Dog oldestDog = findOldestDog();
        dogs.remove(oldestDog); // Remove the oldest dog from the queue
        return oldestDog;
    }

    // Find and remove the oldest cat based on intake date (FIFO)
    public Cat dequeueOldestCat() {
        Cat oldestCat = findOldestCat();
        cats.remove(oldestCat); // Remove the oldest cat from the queue
        return oldestCat;
    }

    // Helper method to find the oldest dog based on intake date
    private Dog findOldestDog() {
        Dog oldestDog = null;
        for (Dog dog : dogs) {
            if (oldestDog == null || dog.getIntakeDate().isBefore(oldestDog.getIntakeDate())) {
                oldestDog = dog;
            }
        }
        return oldestDog;
    }

    // Helper method to find the oldest cat based on intake date
    private Cat findOldestCat() {
        Cat oldestCat = null;
        for (Cat cat : cats) {
            if (oldestCat == null || cat.getIntakeDate().isBefore(oldestCat.getIntakeDate())) {
                oldestCat = cat;
            }
        }
        return oldestCat;
    }

    // Helper method to get a random intake date within the past 6 months
    private LocalDate getRandomIntakeDate() {
        Random random = new Random();
        LocalDate currentDate = LocalDate.now();
        int daysAgo = random.nextInt(180); // Random date within the last 6 months (180 days)
        return currentDate.minusDays(daysAgo);
    }

    // Method to populate mock animals with gender-appropriate names and realistic intake dates
    public void populateMockAnimals() {
        // Gender-specific names for dogs
        String[][] dogsData = {
                {"Max", "Male"}, {"Charlie", "Male"}, {"Buddy", "Male"}, {"Rocky", "Male"}, {"Jake", "Male"},
                {"Bailey", "Female"}, {"Milo", "Male"}, {"Bentley", "Male"}, {"Duke", "Male"}, {"Tucker", "Male"},
                {"Oliver", "Male"}, {"Bear", "Male"}, {"Jack", "Male"}, {"Cooper", "Male"}, {"Toby", "Male"}
        };

        // Gender-specific names for cats
        String[][] catsData = {
                {"Luna", "Female"}, {"Bella", "Female"}, {"Lucy", "Female"}, {"Kitty", "Female"}, {"Nala", "Female"},
                {"Chloe", "Female"}, {"Simba", "Male"}, {"Milo", "Male"}, {"Leo", "Male"}, {"Loki", "Male"},
                {"Charlie", "Male"}, {"Oreo", "Male"}, {"Jasper", "Male"}, {"Oliver", "Male"}, {"Shadow", "Male"}
        };

        String[] dogBreeds = {"Labrador Retriever", "German Shepherd", "Golden Retriever", "Bulldog", "Beagle", "Poodle", "Rottweiler", "Boxer", "Dachshund", "Siberian Husky"};
        String[] catBreeds = {"Siamese", "Persian", "Maine Coon", "Ragdoll", "Bengal", "Sphynx", "British Shorthair", "Scottish Fold", "Abyssinian", "Russian Blue"};

        Random random = new Random();

        // Populate dogs
        for (int i = 0; i < dogsData.length; i++) {
            String dogName = dogsData[i][0];
            String dogSex = dogsData[i][1];
            String dogBreed = dogBreeds[random.nextInt(dogBreeds.length)];
            int dogAge = random.nextInt(10) + 1; // Random age between 1 and 10
            LocalDate randomIntakeDate = getRandomIntakeDate(); // Random intake date within 6 months
            enqueue(new Dog(dogName, dogBreed, dogAge, dogSex, randomIntakeDate));
        }

        // Populate cats
        for (int i = 0; i < catsData.length; i++) {
            String catName = catsData[i][0];
            String catSex = catsData[i][1];
            String catBreed = catBreeds[random.nextInt(catBreeds.length)];
            int catAge = random.nextInt(10) + 1; // Random age between 1 and 10
            LocalDate randomIntakeDate = getRandomIntakeDate(); // Random intake date within 6 months
            enqueue(new Cat(catName, catBreed, catAge, catSex, randomIntakeDate));
        }
    }

    // Display available animals in the shelter
    public void displayAvailableAnimals() {
        System.out.println("🐕🐈 Animals Looking For Furever Homes 🐕🐈");

        Dog oldestDog = findOldestDog();
        Cat oldestCat = findOldestCat();

        System.out.println("🐕 Dogs in the shelter:");
        for (Dog dog : dogs) {
            if (dog == oldestDog) {
                System.out.println(dog + " ⭐"); // Add a star to the oldest dog
            } else {
                System.out.println(dog);
            }
        }

        System.out.println("\n🐈 Cats in the shelter:");
        for (Cat cat : cats) {
            if (cat == oldestCat) {
                System.out.println(cat + " ⭐"); // Add a star to the oldest cat
            } else {
                System.out.println(cat);
            }
        }
    }

    public static void main(String[] args) {
        AnimalShelter shelter = new AnimalShelter();
        shelter.populateMockAnimals();

        Scanner scanner = new Scanner(System.in);
        System.out.println("🏠 Welcome to the Keyin Animal Adoption Center! 🏠");

        System.out.print("\nPlease enter your name: ");
        String adopterName = scanner.nextLine();

        System.out.print("Please enter your contact information (email or phone): ");
        String contactInfo = scanner.nextLine();

        System.out.println("\nThank you! Press Enter to continue...");
        scanner.nextLine(); // Pause until Enter is pressed

        // Display available animals before adoption process
        shelter.displayAvailableAnimals();

        System.out.println("\nReady to adopt? Press Enter to continue...");
        scanner.nextLine(); // Pause again for user to continue

        System.out.println("\nPlease choose from the following options:");
        System.out.println("1. Adopt the oldest animal (doesn't matter if it's a dog or cat).");
        System.out.println("2. Adopt the oldest dog.");
        System.out.println("3. Adopt the oldest cat.");
        System.out.print("Please enter your choice (1, 2, or 3): ");
        int choice = scanner.nextInt();

        Animal adoptedAnimal = null;

        // Process the adoption based on user choice (FIFO order)
        switch (choice) {
            case 1:
                adoptedAnimal = shelter.dequeueAny();
                break;
            case 2:
                adoptedAnimal = shelter.dequeueOldestDog();
                break;
            case 3:
                adoptedAnimal = shelter.dequeueOldestCat();
                break;
            default:
                System.out.println("Invalid choice. Please restart the process.");
                return;
        }

        // If an animal is successfully adopted
        if (adoptedAnimal != null) {
            System.out.println(adoptedAnimal.generateAdoptionMessage(adopterName, contactInfo));
            System.out.println("\nThank you for adopting from Keyin Animal Adoption Center! 🐾");
        } else {
            System.out.println("Sorry, there are no animals available for adoption at the moment.");
        }
    }
}
