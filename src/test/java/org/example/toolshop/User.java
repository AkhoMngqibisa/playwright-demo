package org.example.toolshop;

public record User(
        String first_name,
        String last_name,
        String address,
        String city,
        String state,
        String country,
        String postcode,
        String phone,
        String dob,
        String password,
        String email
) {

    public static User randomUser() {
        return new User()
    }
}
