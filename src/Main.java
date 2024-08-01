import java.util.Comparator;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;


public class Main {



    public static class PhoneBook{
        private static final SortedMap<String, String> list = new TreeMap<>(Comparator.naturalOrder());

        public static void addContact(String name, String number){
            if(!list.isEmpty() && list.containsKey(name)){
                System.out.println("This contact has already been saved, do you wish to change it? (Y/N)");
                char choice;
                Scanner scanner = new Scanner(System.in);
                choice = scanner.next().charAt(0);
                if(choice == 'Y'){
                    changeNumber(name, number);
                    System.out.println("The contact has been changed into:\n"+name+"\n\t"+ list.get(name));
                }
            } else
                list.put(name, number);
            System.out.println("Operation concluded");
        }

        public static void changeNumber(String name, String number){
            list.replace(name, number);
        }

        public static void changeName(String old_name, String new_name){
            if(old_name.equals(new_name)){
                String tmp = list.get(old_name);
                list.remove(old_name);
                list.put(new_name, tmp);
            }
        }

        public static void removeContact(String name){
            list.remove(name);
            System.out.println("Operation concluded");
        }

        public static String getNumber(String name){
            String ret =  list.get(name);
            if(ret==null)
                ret = "x";
            return ret;
        }
    }



    public static void main(String[] args) {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hi User, this interface allows you to save contacts");
        boolean in = true;

        // Regex pattern
        final String phoneNumberPattern = "^(\\+?[0-9]{1,3})? ?[0-9]{3} ?[0-9]{3} ?[0-9]{4}$";
        final String namePattern = "^[A-Za-z0-9_]+( [A-Za-z0-9_]+)*$";

        while (in) {
            System.out.println("Choose an option among the ones below\n 1) Get the number of an existing contact\n 2) Add a new contact\n 3) Change an existing contact\n 4) Remove an existing contact\n 5) Exit");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1: {
                    System.out.println("Whose number are you looking for?");
                    String name = scanner.nextLine();
                    String number = PhoneBook.getNumber(name);
                    if ("x".equals(number)) {
                        System.out.println("There is no contact with this name");
                    } else {
                        System.out.println(number);
                    }
                    break;
                }
                case 2: {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    // Validate name
                    if (!name.matches(namePattern)) {
                        System.out.println("Invalid name format.");
                        break;
                    }

                    System.out.print("Number: ");
                    String number = scanner.nextLine();

                    // Validate number
                    if (!number.matches(phoneNumberPattern)) {
                        System.out.println("Invalid phone number format.");
                        break;
                    }

                    PhoneBook.addContact(name, number);
                    break;
                }
                case 3: {
                    System.out.print("Old name: ");
                    String oldName = scanner.nextLine();
                    String number = PhoneBook.getNumber(oldName);
                    if ("x".equals(number)) {
                        System.out.println("There is no contact with this name");
                        break;
                    }

                    System.out.print("New name (press 'Enter' to skip): ");
                    String newName = scanner.nextLine();
                    if (!newName.isEmpty() && newName.matches(namePattern)) {
                        PhoneBook.changeName(oldName, newName);
                    }

                    System.out.print("New number (press 'Enter' to skip): ");
                    String newNumber = scanner.nextLine();
                    if (!newNumber.isEmpty() && newNumber.matches(phoneNumberPattern)) {
                        PhoneBook.changeNumber(newName, newNumber);
                    }
                    break;
                }
                case 4: {
                    System.out.print("Type the name of the contact (to cancel press 'Enter'): ");
                    String name = scanner.nextLine();
                    if (!name.isEmpty()) {
                        PhoneBook.removeContact(name);
                    }
                    break;
                }
                case 5: {
                    System.out.println("Goodbye!");
                    in = false;
                    break;
                }
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
        scanner.close();
    }
}
