package socialMediaFinal;

import java.util.Scanner;

import lists.AList;
import lists.ListInterface;

public class Account {
	
	private static final int CHARS_LIMIT = 60;
	private String username; 
	private String password;
	private String bio; 
	private ListInterface<String> postList = new AList<String>();
	
	public Account(String username, String password) {
		this(username, password, "");
	} 
	
	public Account(String username, String password, String bio) {
		this.username = username;
		this.password = password;
		this.bio = bio;
	}
	
	public boolean equals(Account notUser) {
		return this.username.equals(notUser.getUsername());
	}
	
	public boolean verifyPassword(String password) {
		return this.password.equals(password);
	}
	
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	//Takes input from user and changes bio
	public String changeBio(Scanner input) {
		System.out.print("Enter new bio or 0 to return: ");
		boolean invalid = true;
		String bio = null;
		while(invalid) {
			bio = input.nextLine();
			if(bio.equals("0")) {
				System.out.println("Changing bio cancelled.");
				bio = null;
				break;
			}
			else if(CHARS_LIMIT <= bio.length()) {
				System.out.println("Bio is too long");
			}
			else {
				invalid = false;
			}
		}
		
		if(bio != null) {
			setBio(bio);
		}
		
		return bio;
	}
	
	//Asks user for a post and adds it
	public String addPost(Scanner input) {
		System.out.print("Enter the post you wish to add or 0 to return: ");
		boolean invalid = true;
		String post = null;
		while(invalid) {
			post = input.nextLine();
			if(post.equals("0")) {
				System.out.println("Adding post cancelled");
				post = null;
				break;
			}
			else if(CHARS_LIMIT <= post.length()) {
				System.out.println("Post is too long.");
			}
			else if(postList.contains(post)) {
				System.out.println("Post already exists in list.");
			}
			else {
				invalid = false;
			}
		}
		if(post != null) {
			postList.add(post);
		}
		
		return post; 
	}
	
	//Asks user which post they want to remove and removes it
	public String removePost(Scanner input) {
		String removedPost = null; 
		if(postList.isEmpty()) {
			System.out.println("You haven't posted anything yet.");
		}
		else {
			displayPosts();
			String message = "Enter the number of the post you wish to delete or 0 to return: ";
			int min = 0; 
			int max = postList.getLength();
			int choice = App.getUserChoice(min, max, message);
			if(choice != 0 ) {
				removedPost = postList.remove(choice);
			}
		}
		return removedPost;
	}

	
	public String getUsername() { 
		return username; 
	}
	
	//Displays username and bio 
	public void displayAccount() {
		System.out.println("\n" + username + ": " + bio);
	} 

	public void displayPosts() {
		if(postList.isEmpty()) {
			System.out.println();
			System.out.println(username + " hasn't posted anything yet.");
		}
		else {
			for(int i = 1; i <= postList.getLength(); ++i) {
				System.out.println("\t" + i + ") " + postList.getEntry(i));
			}
		}
	}
	
	// For testing only
	
	public void addPost(String post) {
		postList.add(post);
	}
	
	
	
}
