import static java.lang.Math.*;

/**
 * Create a database which record the city name and coordinates in a binary search tree
 * Used methods
 *      => Insert data
 *      => Delete data by name
 *      => Search by name
 *      => Print data in descending order
 *      => Find distances from a point to the city
 *
 * @author 2018/E/083
 */

public class CityDB {
    public static Node root;

    public CityDB() {
        this.root = null;
    }

    public static void main(String[] args) {
        CityDB db = new CityDB(); // Create a CityDB object
        //Insert data to database
        db.insert("colombo", 6.927079, 79.861244);
        db.insert("chicago", 41.881832, -87.623177);
        db.insert("sydney", -33.865143, 151.209900);
        System.out.println("Print database in descending order");
        db.dispDescending(db.root); // Print database
        System.out.println("Search city by name:");
        db.searchByName(root, "chicago");
        System.out.println("Print all cities within a given distance of a specified point:");
        db.dispAllDistance(root, 6.927079, 79.861244);
        db.deleteByName(root, "sydney"); // Delete data from database
        System.out.println("Print database after delete sydney city records:");
        db.dispDescending(db.root); // Print database
    }

    /**
     * Insert data to binary search tree
     *
     * @param name  = City name
     * @param lati  = Latitude
     * @param longi = Longitude
     */
    public void insert(String name, double longi, double lati) {
        Node newNode = new Node(name, longi, lati); //make a new node
        if (root == null) {
            //If BST is empty newNode define as root
            root = newNode;
            return;
        }
        Node current = root;
        Node parent = null;
        while (true) {
            parent = current;
            if (name.compareTo(current.cityName) < 0) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    return;
                }
            }
            else {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    return;
                }
            }
        }
    }

    /**
     * Print all city records in descending order by their city name
     *
     * @param root = root node
     */
    public void dispDescending(Node root) {
        if (root != null) {
            dispDescending(root.right);
            System.out.println(root.cityName + "\t" + root.longitude + "\t" + root.latitude);
            dispDescending(root.left);
        }
    }

    /**
     * delete database recode by name
     *
     * @param root - Node
     * @param name - City name which we want to delete from database
     * @return - retrun the deleted record
     */
    public Node deleteByName(Node root, String name) {
        if (root == null) {
            return null; //If tree is empty return null
        }
        else {
            if (name.compareTo(root.cityName) > 0) {
                root.right = deleteByName(root.right, name);
            }
            else if (name.compareTo(root.cityName) < 0) {
                root.left = deleteByName(root.left, name);
            }
            else {
                if (root.left == null) {
                    return root.right;
                }
                else if (root.right == null) {
                    return root.left;
                }
                root.cityName = minVal(root.right);
                root.right = deleteByName(root.left, root.cityName);
            }
        }
        return root;
    }

    /**
     * find the minimum record name from database
     *
     * @param root - input node
     * @return - minimum node
     */
    String minVal(Node root) {
        String min = root.cityName; //Define root node as minimum element
        while (root.left != null) {
            min = root.left.cityName;
            root = root.left;
        }
        return min;
    }

    /**
     * Search data from database using city name
     *
     * @param root - Input node
     * @param name - The name which we want to find
     * @return - return root node
     */
    private Node searchByName(Node root, String name) {
        if (root == null) {
            // If tree is empty
            System.out.println(name + " Not found");
            return root;
        }
        if (name.compareTo(root.cityName) == 0) {
            // If city name found
            System.out.println("The city " + name + " location is (" + root.longitude + "\t" + root.latitude + ")");
        }
        else if (name.compareTo(root.cityName) > 0) {
            //value less than root's key
            return searchByName(root.right, name);
        }
        else if (name.compareTo(root.cityName) < 0) {
            //Value greater than root's key
            return searchByName(root.left, name);
        }
        return root;
    }

    /**
     * Calculate distance to city from specified point
     *
     * @param root      - Input node
     * @param longitude - longitude coordinate of the city
     * @param latitude  - latitude coordinate of the city
     * @return - distance from specified point to city
     */
    public double calDist(Node root, double longitude, double latitude) {
        double x = root.longitude; // longitude coordinate of the city
        double y = root.latitude; // Latitude coordinate of the city
        double xT = pow(x - longitude, 2); // square of the longitude value difference
        double yT = pow(y - latitude, 2); // square of the latitude value difference
        double dist = sqrt(xT + yT); // distance to city from point
        return dist;
    }

    /**
     * Print all the distances from specified point to cities
     *
     * @param root      - Current node
     * @param longitude - X coordinate of the given point
     * @param latitude  - Y coordinate of the given point
     */
    public void dispAllDistance(Node root, double longitude, double latitude) {
        if (root != null) {
            dispAllDistance(root.left, longitude, latitude); // Traverse to left node of the root node
            double cor = calDist(root, longitude, latitude); // Calculate the distance
            System.out.println(root.cityName + " " + cor); // Print the distance of the city
            dispAllDistance(root.right, longitude, latitude); // Traverse to right node of the root node
        }
    }

    /**
     * Define a Node class
     */
    class Node {
        String cityName;
        double longitude;
        double latitude;
        Node left;
        Node right;

        /**
         * Node method
         *
         * @param cityName  - Name of the city
         * @param latitude  - Latitude coordinate
         * @param longitude - Longitude coordinate
         */
        public Node(String cityName, double longitude, double latitude) {
            this.cityName = cityName;
            this.latitude = latitude;
            this.longitude = longitude;
            left = null;
            right = null;
        }
    }
}