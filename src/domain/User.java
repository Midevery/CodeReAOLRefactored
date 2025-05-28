package domain;

public class User {
    private String username;
    private String password;
    private Role role;
    private int balance;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = 0;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public int getBalance() { return balance; }

    public void deposit(int amount) { balance += amount; }

    public boolean deductBalance(int amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }
}
