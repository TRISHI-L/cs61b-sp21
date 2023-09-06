package capers;

import java.io.File;
import java.io.Serializable;
import static capers.Utils.*;
import capers.CapersRepository;

/** Represents a dog that can be serialized.
 * @author TODO
*/
public class Dog implements Serializable { // TODO

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = Utils.join(".capers", "dogs");

    /** Age of dog. */
    private int age;
    /** Breed of dog. */
    private String breed;
    /** Name of dog. */
    private String name;

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        // TODO (hint: look at the Utils file)
        /** You should be using readObject.
         * This should be similar to saveDog
         * except you’re loading a Dog from your filesystem instead of writing it!
         */
        File dogsFile = Utils.join(DOG_FOLDER, name);
        if (!dogsFile.exists()) {
            return null;
        }
        else {
            Dog d = readObject(dogsFile,Dog.class);
            return d;
        }
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        /** saveDog: You should be using writeObject,
         *  since Dogs aren’t Strings so we want to be able to serialize them.
         *  Make sure you’re writing your dog to a File object that represents a file and not a folder! */
        File dogsFile = Utils.join(DOG_FOLDER, name);
        writeObject(dogsFile, this);
    }

    @Override
    public String toString() {
        return String.format(
            "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
            name, breed, age);
    }

}
