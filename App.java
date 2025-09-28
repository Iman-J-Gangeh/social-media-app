package socialMediaFinal;

import java.util.Iterator;
import java.util.Scanner;

import hashDictionary.DictionaryInterface;
import hashDictionary.HashedDictionary;
import graphs.DirectedGraph;
import graphs.VertexInterface;
public class App {

	private static final Scanner INPUT = new Scanner(System.in);
	private static final int ALLOTED_ATTEMPTS = 20;
	private static final int MAX_CHARS = 30;


	public static void main(String[] args) {

		//Dictionary to hold list of users
		DictionaryInterface<String, Account> userList = new HashedDictionary<String, Account>();
		DirectedGraph<String> friendsGraph = new DirectedGraph<>();
		
		
		
		//populating userList for testing 
		userList.add("iman", new Account("iman", "gangeh"));
		userList.add("user1", new Account("user1", "password", "bio"));
		userList.add("user2", new Account("user2", "password", "bio"));
		userList.add("user3", new Account("user3", "password"));
		
		if(!userList.isEmpty()) {
			Iterator<String> userIterator = userList.getKeyIterator();
			while(userIterator.hasNext()) {
				friendsGraph.addVertex(userIterator.next());
			}
		}
		
		Account user1 = userList.getValue("user1");
		addFriend(user1.getUsername(), "iman", friendsGraph);
		addFriend(user1.getUsername(), "user2", friendsGraph);
		addFriend(user1.getUsername(), "user3", friendsGraph);
		user1.addPost("Post 1");
		user1.addPost("Post 2");
		user1.addPost("Post 3");
		Account user2 = userList.getValue("user2");
		addFriend(user2.getUsername(), "user1", friendsGraph);
		user2.addPost("Holiday post");
		Account user3 = userList.getValue("user3");
		addFriend(user3.getUsername(), "iman", friendsGraph);
		user3.addPost("New Post");

		boolean exited = false;

		//App is running until loop is exited
		while(!exited) {
			System.out.println("Welcome to facebook!");
			Account user = login(userList, friendsGraph);
			

			if(user != null) {
				runApp(user, userList, friendsGraph);
			} 
			else {
				exited = true;
				System.out.println("User exited the app.");
			}
		}
	}

	//allows the user to login or create an account, returns the users account or null if login fails
	public static Account login(DictionaryInterface<String, Account> userList, DirectedGraph<String> friendsGraph) {
		boolean loggingIn = true;
		Account user = null;
		//executes while user hasn't logged in or exited

		String message = "Enter 1 to login, 2 to create account, or 0 to exit: ";
		do {
			int choice = getUserChoice(0, 2, message);

			//Depending on choice initiates login, creating account, or exits

			switch(choice) {
			case 1:
				String usernameMessage = "Enter your username, or 0 to return to main menu: ";
				String errorMessage = "Username does not exist";
				String username = getUsername(usernameMessage, errorMessage, userList);
				if(username != null) {
					System.out.println("Username verified");
					user = userList.getValue(username);
					boolean verified = verifyPassword(username, user);
					if(verified) {
						System.out.println("Password verified.");
						loggingIn = false;
					}
				}
				break;
			case 2: 
				user = createAccount(userList, friendsGraph);
				if(user != null) {
					loggingIn = false;
				}
				else {
					System.out.println("Error in creating account.");
				}
				break;
			case 0: 
				loggingIn = false; 
				break;
			}


		} while(loggingIn); 

		return user;
	}

//Helper method for login: 
	
	//Recieves a username from the user and checks whether it exists in the userList
	public static String getUsername(String inputMessage, String errorMessage, DictionaryInterface<String, Account> userList) {
		String username = null;
		String inputedValue;
		int counter = 0; 
		boolean verified = false;
		while(!verified && counter <= ALLOTED_ATTEMPTS) {
			System.out.print(inputMessage);
			inputedValue = INPUT.nextLine();
			if(inputedValue.equals("0")) {
				break;
			}
			else if(userList.contains(inputedValue)) {
				username = inputedValue;
				verified = true; 
			}
			else {
				System.out.println(errorMessage);

			}
			++counter;
		}

		return username; 
	}

	//recieves a password from user and verifies it
	public static boolean verifyPassword(String username, Account user) {
		boolean verified = false;
		String inputedValue;
		int counter = 0; 
		while(!verified && counter <= ALLOTED_ATTEMPTS) {
			System.out.print("Enter your password, or 0 to return to main menu: ");
			inputedValue = INPUT.nextLine();
			if(inputedValue.equals("0")) {
				break;
			}
			else {
				verified = user.verifyPassword(inputedValue);
				if(!verified) {
					System.out.println("Wrong password.");
				}
			}
		}

		return verified;
	}

//end of login helper methods


	//Allows the user to create a new account, returns null if user fails to create account
	public static Account createAccount(DictionaryInterface<String, Account> userList, DirectedGraph<String> friendsGraph) {
		Account user = null; 
		String username = createUsername(userList);
		if(username != null) {
			String password = createPassword();
			if(password != null) {
				user = new Account(username, password);
				userList.add(username, user);
				friendsGraph.addVertex(user.getUsername());
			}
		}
		return user;
	}

//Helper methods for createAccount: 

	//allows the user to create a username, returns username if created null if not
	public static String createUsername(DictionaryInterface<String, Account> userList) {
		String username = null;
		boolean createdUsername = false;
		while(!createdUsername) {
			System.out.print("Enter your desired username or enter 0 to return to main menu: ");
			username = INPUT.nextLine(); 
			if(username.equals("0")) {
				username = null;
				break;
			}
			else if(MAX_CHARS <= username.length()) {
				System.out.println("Username is too long.");
			}
			else if(username.length() <= 0) {
				System.out.println("Username is too short.");
			}
			else if(userList.contains(username)) {
				System.out.println("Username is taken.");
			}
			else {
				createdUsername = true;
			}
		}

		return username;
	}

	//allows the user to create a password, returns password if created null if not
	public static String createPassword() {
		String password = null;
		boolean createdPassword = false; 
		while(!createdPassword) {
			System.out.print("Enter your desired password or enter 0 to return to main menu: ");
			password = INPUT.nextLine();
			if(password.equals("0")) {
				password = null;
				break;
			}
			else if(password.length() <= MAX_CHARS) {
				System.out.print("Enter password again to verify: ");
				String verify = INPUT.nextLine();
				if(password.equals(verify)) {
					createdPassword = true; 
				}
				else {
					System.out.println("Passwords must match.");
				}
			}
			else if(password.length() <= 0) {
				System.out.println("Password is too short.");
			}
			else {
				System.out.println("Password is too long.");
			}
		}

		return password; 
	}

//end of helper methods 

	//Facilitates the user searching for another account, returns the account the user searched for
	public static Account search(DictionaryInterface<String, Account> userList) {
		Account searched = null;
		String inputMessage = "Enter username you want to search for or 0 to exit: ";
		String errorMessage = "No results found";
		String username = getUsername(inputMessage, errorMessage, userList);
		if(username != null) {
			searched = userList.getValue(username);
		}



		return searched;
	}

	//gives the users options based on the account they searched
	public static void searchActions(Account user, Account searchedFor, DirectedGraph<String> friendsGraph) {
		int choice; 
		boolean isUser = searchedFor.getUsername().equals(user.getUsername());
		boolean goneBack = false;
		while(!goneBack) {
			boolean isUserFriend = isFriends(user.getUsername(), searchedFor.getUsername(), friendsGraph);
			int min = 0; 
			int max;
			String searchMessage = "Enter 1 to view profile, ";
			//if the user has searched for themselves 
			if(isUser) {
				accountActions(user, isUser, friendsGraph);
			}
			//if the user has searched for someone who isn't their friend
			else if(!isUserFriend) {
				searchMessage += "2 to add acccount as friend, or 0 to go back: ";
				max = 2;
				choice = getUserChoice(min, max, searchMessage);
				switch(choice) {
				case 1: 
					accountActions(searchedFor, isUser, friendsGraph);
					break;
				case 2: 
					addFriend(user.getUsername(), searchedFor.getUsername(), friendsGraph);
					break;
				case 0: 
					goneBack = true;
					break;
				}
			}
			//if the user has searched for someone who is their friend
			else {
				searchMessage += "2 to remove account from friends list, or 0 to go back: ";
				max = 2;
				choice = getUserChoice(min, max, searchMessage);
				switch(choice) {
				case 1: 
					accountActions(searchedFor, isUser, friendsGraph);
					break;
				case 2: 
					removeFriend(user.getUsername(), searchedFor.getUsername(), friendsGraph);
					break;
				case 0:
					goneBack = true;
					break;
				}
			}			
		}

	}

	//Executes only after user has logged in, stops executing when user chooses to logout
	public static void runApp(Account user, DictionaryInterface<String, Account> userList, DirectedGraph<String> friendsGraph) {
		boolean logout = false;
		//Actions after logging in
		System.out.println("\nWelcome " + user.getUsername() + "!");
		String message = "Enter 1 to search, 2 to go to account, or 0 to logout: ";
		do {
			int choice = getUserChoice(0, 2, message);

			switch(choice) {
			case 1:
				//returns the account the user is searching for or null if unsuccessful
				Account searchedFor = search(userList);
				//if the searchedFor user isn't the 
				if(searchedFor != null) {
					searchActions(user, searchedFor, friendsGraph);
				}
				break;
				//allows user to view and update their account
			case 2: 
				boolean isUser = true;
				accountActions(user, isUser, friendsGraph);
				break;
			case 0: 
				user = null;
				logout = true;
				System.out.println("You have successfully been logged out.\n");		
				break;

			}
		} while(!logout);
	}

	//gives the user options based on whether the user is viewing their account or another
	public static void accountActions(Account account, boolean isUser, DirectedGraph<String> friendsGraph) {
		int choice;
		boolean exitedAccountView = false;
		while(!exitedAccountView) {
			account.displayAccount();
			if(isUser) {
				String profileMessage = "Enter 1 to add a post, 2 to remove a post, 3 to display posts, "
						+ "4 to display friends list, 5 to edit bio, or 0 to return: ";
				int min = 0;
				int max = 4;
				choice = getUserChoice(min, max, profileMessage);

				switch(choice) {
				//Allow user to add post
				case 1: 
					String post = account.addPost(INPUT);
					if(post != null) {
						System.out.println("Post successfully added.");
						account.displayPosts();
					}
					else {
						System.out.println("user didn't add post");
					}
					break;	
					//Allow user to change bio
				case 2: 
					String removed = account.removePost(INPUT);
					if(removed != null) {
						System.out.println("Successfully removed: " + removed);
					}
					break;
					//Displays friend list to user 
				case 3: 
					System.out.print("");
					account.displayPosts();
					break;
				case 4: 
					displayFriendsList(account, friendsGraph);
					break;
				case 5: 
					String bio = account.changeBio(INPUT);
					if(bio != null) {
						System.out.println("Bio successfully changed.");
					}
					break;
				case 0: 
					exitedAccountView = true;
					break;
				}
			}
			else {
				String profileMessage = "Enter 1 to look at friend list, "
						+ "2 to display posts or 0 to return: ";
				int min = 0;
				int max = 2;
				choice = getUserChoice(min, max, profileMessage);

				switch(choice) {
				case 1: 
					displayFriendsList(account, friendsGraph);
					break;
				case 2: 
					account.displayPosts();
					break;
				case 0: 
					exitedAccountView = true;
					break;
				}
			}

		}
	}

	/* 
 	takes in the upper and lower bounds and message to be displayed gets a user input within bounds (inclusive)
 	returns the verified input 
	 */
	public static int getUserChoice(int min, int max, String message) {
		//Gets the users choice 
		boolean valid = false;
		int choice = -1;
		while(!valid) {
			System.out.print(message);
			try {
				choice = INPUT.nextInt();
				if(min <= choice && choice <= max) {
					valid = true;
				}
				else {
					System.out.println("Invalid input.");
				}
			} catch (Exception e) {
				System.out.println("Invalid input.");
			}
			INPUT.nextLine();
		}

		return choice;
	}

	//Displays an accounts list of friends
	public static void displayFriendsList(Account account, DirectedGraph<String> friendsGraph) {
		VertexInterface<String> accountVertex = friendsGraph.getVertex(account.getUsername());
		Iterator<VertexInterface<String>> friendsIterator = accountVertex.getNeighborIterator();
		System.out.println(account.getUsername() + "'s list of friends:");
		while(friendsIterator.hasNext()) {
			VertexInterface<String> currentFriend = friendsIterator.next();
			String friendName = currentFriend.getLabel();
			System.out.println("\t" + friendName);
		}
	}

	public static boolean isFriends(String user, String potentialFriend, DirectedGraph<String> friendsGraph) {
		boolean isFriends = false;
		VertexInterface<String> accountVertex = friendsGraph.getVertex(user);
		Iterator<VertexInterface<String>> friendsIterator = accountVertex.getNeighborIterator();
		while(friendsIterator.hasNext()) {
			if(potentialFriend.equals(friendsIterator.next().getLabel())) {
				isFriends = true;
				break;
			}
		}
		return isFriends;
	}

	public static void addFriend(String user, String newFriend, DirectedGraph<String> friendsGraph) {
		friendsGraph.addEdge(user, newFriend);
		System.out.println(newFriend + " was added to " + user + "'s friend list.");
	}
	
	public static void removeFriend(String user, String oldFriend, DirectedGraph<String> friendsGraph) {
		friendsGraph.removeEdge(user, oldFriend);
		System.out.println(oldFriend + " was removed from your friend list.");
	}

}

