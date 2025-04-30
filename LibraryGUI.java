import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

abstract class LibraryItem
{
    protected String title;
    protected String author;
    protected String ISBN;
    protected boolean isAvailable = true;
    protected int quantityAvailable ;
    /// getters, setters & constructor
    // Constructor
    public LibraryItem(String Title, String Author, String ISBN,int Copies)
    {
        this.title = Title;
        this.author = Author;
        this.ISBN = ISBN;
        this.quantityAvailable = Copies;
    }
    // title
    public void setTitle(String Title) { this.title = Title; }
    public String getTitle() { return title; }

    // author
    public void setAuthor(String Author) { this.author = Author; }
    public String getAuthor() { return author; }

    // ISBN
    public void setISBN (String ISBN){ this.ISBN = ISBN; }
    public String getISBN () { return ISBN; }

    // IsAvailable method
    public void setAvailability(boolean IsAvailable ) { isAvailable = IsAvailable; }
    public boolean getAvailability() { return isAvailable; }

    public abstract void Borrow();
    public void returnItem()
    {
        System.out.printf("Item: %s with ISBN: %s is returned successfully %n",title,ISBN);
        isAvailable = true;
    }
}
interface Borrowable // -> for borrowable items only
{
    void Borrow();
}
class PhysicalBook extends LibraryItem implements Borrowable
{
    // Constructor, calls the parent class
    public PhysicalBook(String title, String author, String ISBN,int Copies) { super(title, author, ISBN, Copies); }

    @Override
    public void Borrow() {
        if (getAvailability() && quantityAvailable>0)
        {
            System.out.printf("Item: %s with ISBN: %s is borrowed successfully %n",title,ISBN);
            quantityAvailable--;
            if(quantityAvailable == 0)
            {
                setAvailability(false);
            }
        }
        else
        {
            System.out.printf("Item: %s with ISBN: %s is not available to be borrowed %n",title,ISBN);
        }
    }
}
class EBook extends LibraryItem implements Borrowable
{
    // Constructor, calls the parent class
    public EBook(String title, String author, String ISBN,int Copies) { super(title, author, ISBN, Copies); }

    @Override
    public void Borrow()
    {
        // EBook is always available, so now conditions
        System.out.printf("Item: %s with ISBN: %s is borrowed successfully %n",title,ISBN);
    }
}
class ReferenceBook extends LibraryItem
{
    // Constructor, calls the parent class
    public ReferenceBook(String title, String author, String ISBN,int Copies) { super(title, author, ISBN, Copies); }
    @Override
    public void Borrow()
    {
        // Reference books can't be borrowed
        System.out.printf("Item: %s with ISBN: %s is a reference book and can't be borrowed %n",title,ISBN);
    }
}
class CD extends LibraryItem implements Borrowable
{
    // Constructor, calls the parent class
    public CD (String title, String author, String ISBN,int Copies) { super(title, author, ISBN, Copies); }

    @Override
    public void Borrow() {
        if (getAvailability() && quantityAvailable>0)
        {
            System.out.printf("Item: %s with ISBN: %s is borrowed successfully %n",title,ISBN);
            quantityAvailable--;
            if(quantityAvailable == 0)
            {
                setAvailability(false);
            }
        }
        else
        {
            System.out.printf("Item: %s with ISBN: %s is not available to be borrowed %n",title,ISBN);
        }
    }
}
class DVD extends LibraryItem implements Borrowable
{
    // Constructor, calls the parent class
    public DVD (String title, String author, String ISBN,int Copies) { super(title, author, ISBN, Copies); }
    @Override
    public void Borrow() {
        if (getAvailability() && quantityAvailable>0)
        {
            System.out.printf("Item: %s with ISBN: %s is borrowed successfully %n",title,ISBN);
            quantityAvailable--;
            if(quantityAvailable == 0)
            {
                setAvailability(false);
            }
        }
        else
        {
            System.out.printf("Item: %s with ISBN: %s is not available to be borrowed %n",title,ISBN);
        }
    }
}
class Journal extends LibraryItem
{
    // Constructor, calls the parent class
    public Journal (String title, String author, String ISBN,int Copies) { super(title, author, ISBN, Copies); }

    @Override
    public void Borrow()
    {
        // Journals can't be borrowed
        System.out.printf("Item: %s with ISBN: %s can't be borrowed %n",title,ISBN);
    }
}

class Library
{
    protected Map<String, LibraryItem> items = new HashMap<>();
    private List<LibraryUser> users = new ArrayList<>();

    public void addItem(LibraryItem item)
    {
        items.put(item.getTitle(), item);
        System.out.println("Item is added successfully");
    }

    public void listAvailableItems()
    {
        System.out.println("Available items: ");
        for (LibraryItem item : items.values())
        {
            if (item.getAvailability())
            {
                System.out.printf("Item title: %s, ISBN: %s,Author: %s %n",item.getTitle(),item.getISBN(),item.getAuthor());
            }
        }
    }

    public void borrowItem(LibraryUser user, String title)
    {
        LibraryItem item = items.get(title);
        if (item != null)
        {
            user.borrowItem(item);
        }
        else
        {
            System.out.println("Item is not found in the library");
        }
    }

    public void returnItem(LibraryUser user, String title)
    {
        LibraryItem item = items.get(title);
        if (item != null)
        {
            user.returnItem(item);
        }
    }

    public List<LibraryUser> getUsers()
    {
        return users;
    }

}



class LibraryUser
{
    private static Library library;
    private String userID;
    private String userName;
    private List<LibraryItem> borrowedItems = new ArrayList<>(); // todo -> create getter and setter

    /// getters, setters & constructor
    // Constructor
    public LibraryUser(String UserID) { this.userID = UserID; }
    public LibraryUser(String UserID, String UserName) { this.userID = UserID; this.userName = UserName; }

    // userID
    public void setUserID(String userID) { this.userID = userID; }
    public String getUserID() { return userID; }

    // userName
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserName() { return userName; }

    public void borrowItem(LibraryItem item)
    {
        // first check if the item is available to be borrowed & if it is borrowable
        if (item.isAvailable && item instanceof Borrowable)
        {
            borrowedItems.add(item);
            ((Borrowable) item).Borrow();
        }
        else
        {
            System.out.printf("Item: %s with ISBN: %s can't be borrowed %n",item.title,item.ISBN);
        }
    }
    public void returnItem(LibraryItem item)
    {
        if (borrowedItems.contains(item))
        {
            borrowedItems.remove(item);
            item.returnItem();
        }
        else
        {
            System.out.printf("Item: %s with ISBN: %s is not borrowed before %n",item.title,item.ISBN);
        }
    }

}


public class LibraryGUI extends JFrame {
    private Library library;
    private JTextArea outputArea;

    public LibraryGUI()
    {
        // Frame Settings / characteristics
        library = new Library();
        setTitle("Library Management System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // welcome message (label)
        JLabel welcomeLabel = new JLabel("Welcome to the Library!!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(welcomeLabel, BorderLayout.NORTH);

        // panels -> first panel: buttons panel, second panel: for the exit button alone
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JPanel exitPanel = new JPanel();

        // buttons initialization
        JButton addButton = new JButton("Add a New Item");
        JButton listButton = new JButton("List Available Items");
        JButton borrowButton = new JButton("Borrow an Item");
        JButton returnButton = new JButton("Return an Item");
        JButton exitButton = new JButton("Exit");

        // buttons adjustments
        exitButton.setPreferredSize(new Dimension(120, 40));
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBackground(Color.RED); // button color
        exitButton.setForeground(Color.WHITE); // button text color
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        // putting buttons in their panels
        buttonPanel.add(addButton);
        buttonPanel.add(listButton);
        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);
        exitPanel.add(exitButton);

        // panels adjustments
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);
        add(exitPanel, BorderLayout.SOUTH);

        /// Buttons Action
        addButton.addActionListener(e -> showAddItemWindow());
        listButton.addActionListener(e -> showAvailableItemsList());
        borrowButton.addActionListener(e -> borrowItemWindow());
        returnButton.addActionListener(e -> returnItemWindow());
        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
//        pack();
    }


    // input form / panel that will take data of new item to be added to library
    private void showAddItemWindow()
    {
        // input text fields
        JTextField titleField = new JTextField(10);
        JTextField authorField = new JTextField(10);
        JTextField isbnField = new JTextField(10);
        JTextField copiesField = new JTextField(5);

        String[] itemTypes = {"PhysicalBook", "EBook", "ReferenceBook", "CD", "DVD", "Journal"};
        // drop down list of available item types
        JComboBox<String> typeBox = new JComboBox<>(itemTypes);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Select Type:"));
        panel.add(typeBox);
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);
        panel.add(new JLabel("Copies:"));
        panel.add(copiesField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION)
        {
            String type = (String) typeBox.getSelectedItem();
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            int copies = Integer.parseInt(copiesField.getText());

            LibraryItem newItem;
            switch (type)
            {
                case "PhysicalBook":
                    newItem = new PhysicalBook(title, author, isbn, copies);
                    break;
                case "EBook":
                    newItem = new EBook(title, author, isbn, copies);
                    break;
                case "ReferenceBook":
                    newItem = new ReferenceBook(title, author, isbn, copies);
                    break;
                case "CD":
                    newItem = new CD(title, author, isbn, copies);
                    break;
                case "DVD":
                    newItem = new DVD(title, author, isbn, copies);
                    break;
                case "Journal":
                    newItem = new Journal(title, author, isbn, copies);
                    break;
                default:
                    newItem = null;
            }
            if (newItem != null)
            {
                library.addItem(newItem);
                JOptionPane.showMessageDialog(this, "Item added successfully!");
            }
        }
    }



    private void showAvailableItemsList() {
        // groups / holds the list of available items in the library
        StringBuilder availableItems = new StringBuilder("Available items:\n");

        // to return a list of available items from the library
        for (LibraryItem item : library.items.values())
        {
            if (item.getAvailability())
            {
                availableItems.append(String.format("Item title: %s, Author: %s, ISBN: %s %n",item.getTitle(),item.getAuthor(),item.getISBN()));
            }
        }
        // display available items in a message dialog / tab
        JOptionPane.showMessageDialog(this, availableItems.toString(), "Available Items", JOptionPane.INFORMATION_MESSAGE);
    }



    private void borrowItemWindow() {
        JTextField userIDField = new JTextField(10);

        // get all items titles that are in the library to choose one to borrow
        List<String> allTitles = new ArrayList<>();
        for (LibraryItem item : library.items.values())
        {
            /// -> Only list the Borrowable items !!! as the non-borrowable items can't be borrowed
            if (item instanceof Borrowable)
            {
                allTitles.add(item.getTitle());
            }
        }

        // handling / check if library is empty or not
        if (allTitles.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "No items in the library.", "Empty Library", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] titlesArray = allTitles.toArray(new String[0]);
        JComboBox<String> itemBox = new JComboBox<>(titlesArray);

        // input form / panel that takes data from user
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("User ID:"));
        panel.add(userIDField);
        panel.add(new JLabel("Select item:"));
        panel.add(itemBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Borrow Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // checking for inputs and data validation
        if (result == JOptionPane.OK_OPTION)
        {
            String userID = userIDField.getText();
            String selectedTitle = (String) itemBox.getSelectedItem();

            if (userID.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Please enter User ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //LibraryUser user = LibraryUser.findUserByID(userID);
            LibraryUser user = new LibraryUser(userID);
            //if (user != null)
            {
                LibraryItem selectedItem = library.items.get(selectedTitle);

                if (selectedItem != null)
                {
                    // Check if item is borrowable
                    if (selectedItem instanceof Borrowable)
                    {
                        if (selectedItem.getAvailability() && selectedItem.quantityAvailable > 0)
                        {
                            library.borrowItem(user, selectedTitle);
                            JOptionPane.showMessageDialog(this, String.format("Item: %s borrowed successfully!", selectedTitle), "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this, String.format("Item: %s is not available for borrowing.", selectedTitle), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, String.format("Item: %s cannot be borrowed (Reference Item).", selectedTitle), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
//            else
//            {
//                JOptionPane.showMessageDialog(this, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
//            }
        }
    }



    private void returnItemWindow() {
        JTextField userIDField = new JTextField(10);
        JTextField titleField = new JTextField(10);

        // input form / panel that takes data from user
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("User ID:"));
        panel.add(userIDField);
        panel.add(new JLabel("Item Title:"));
        panel.add(titleField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Return Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // checking for inputs and data validation
        if (result == JOptionPane.OK_OPTION)
        {
            String userID = userIDField.getText();
            String title = titleField.getText();

            if (userID.isEmpty() || title.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //LibraryUser user = LibraryUser.findUserByID(userID);
            LibraryUser user = new LibraryUser(userID);
            if (user != null) {
                LibraryItem item = library.items.get(title);
                if (item != null)
                {
                    library.returnItem(user, title);
                    JOptionPane.showMessageDialog(this, String.format("Item: %s returned successfully!", title), "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Item not found in library.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args)
    {
        new LibraryGUI();
    }
}
