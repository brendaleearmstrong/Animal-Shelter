import java.util.LinkedList;
import java.util.Queue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

abstract class Animal {
    private String name;
    private String breed;
    private int age;
    private String sex;
    private LocalDate intakeDate;
    private int order;

    public Animal(String name, String breed, int age, String sex) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.sex = sex;
        this.intakeDate = LocalDate.now();
    }

    // Getters and setters (including new ones for sex)

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("%s (%s %s, Breed: %s, Age: %d, Intake Date: %s)",
                name, sex, getClass().getSimpleName(), breed, age, intakeDate.format(formatter));
    }

    public String generateAdoptionMessage(String ownerName) {
        LocalDate adoptionDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String animalType = this instanceof Dog ? "dog" : "cat";

        return String.format("""
            🎉 Congratulations on your new family member, %s! 🎉
            
            You've just adopted %s, a wonderful %s %s.
            
            Adoption Details:
            - Name: %s
            - Type: %s
            - Breed: %s
            - Age: %d years old
            - Sex: %s
            - Adoption Date: %s
            
            %s has been waiting for a loving home since %s, and we're thrilled that you two have found each other!
            
            Remember, adopting a pet is a lifelong commitment. Provide lots of love, care, and attention to your new friend.
            If you have any questions or concerns, don't hesitate to contact us.
            
            Thank you for choosing to adopt and giving %s a forever home!
            
            Best wishes,
            The Animal Shelter Team
            """,
                ownerName, name, sex, animalType, name, animalType, breed, age, sex,
                adoptionDate.format(formatter), name, intakeDate.format(formatter), name);
    }
}

class Dog extends Animal {
    public Dog(String name, String breed, int age, String sex) {
        super(name, breed, age, sex);
    }
}

class Cat extends Animal {
    public Cat(String name, String breed, int age, String sex) {
        super(name, breed, age, sex);
    }
}

class AnimalShelter {
    private Queue<Dog> dogs;
    private Queue<Cat> cats;
    private int order;

    public AnimalShelter() {
        dogs = new LinkedList<>();
        cats = new LinkedList<>();
        order = 0;
    }

    // Enqueue and dequeue methods remain the same

    public void populateMockAnimals() {
        String[] dogNames = {"Max", "Charlie", "Buddy", "Rocky", "Jake", "Jack", "Toby", "Bailey", "Cooper", "Duke", "Tucker", "Oliver", "Bear", "Bentley", "Milo"};
        String[] catNames = {"Luna", "Bella", "Lucy", "Kitty", "Nala", "Chloe", "Simba", "Milo", "Leo", "Loki", "Charlie", "Oreo", "Jasper", "Oliver", "Shadow"};

        String[] dogBreeds = {"Labrador Retriever", "German Shepherd", "Golden Retriever", "Bulldog", "Beagle", "Poodle", "Rottweiler", "Boxer", "Dachshund", "Siberian Husky"};
        String[] catBreeds = {"Siamese", "Persian", "Maine Coon", "Ragdoll", "Bengal", "Sphynx", "British Shorthair", "Scottish Fold", "Abyssinian", "Russian Blue"};

        String[] sexes = {"Male", "Female"};

        Random random = new Random();

        for (int i = 0; i < 15; i++) {
            String dogName = dogNames[i];
            String dogBreed = dogBreeds[random.nextInt(dogBreeds.length)];
            int dogAge = random.nextInt(10) + 1;
            String dogSex = sexes[random.nextInt(2)];
            enqueue(new Dog(dogName, dogBreed, dogAge, dogSex));

            String catName = catNames[i];
            String catBreed = catBreeds[random.nextInt(catBreeds.length)];
            int catAge = random.nextInt(10) + 1;
            String catSex = sexes[random.nextInt(2)];
            enqueue(new Cat(catName, catBreed, catAge, catSex));
        }
    }

    // displayAvailableAnimals method remains the same
}

public class Main {
    public static void main(String[] args) {
        AnimalShelter shelter = new AnimalShelter();

        shelter.populateMockAnimals();

        System.out.println("Initial Animal Shelter State (30 animals):");
        shelter.displayAvailableAnimals();

        System.out.println("\nAdopting animals...");
        String[] adopters = {"Alice", "Bob", "Charlie", "Diana", "Ethan"};
        for (String adopter : adopters) {
            Animal adoptedAnimal = shelter.dequeueAny();
            if (adoptedAnimal != null) {
                System.out.println(adoptedAnimal.generateAdoptionMessage(adopter));
                System.out.println("------------------------------");
            }
        }

        System.out.println("\nAnimal Shelter State After Adoptions:");
        shelter.displayAvailableAnimals();
    }
}