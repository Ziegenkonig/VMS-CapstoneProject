package models;

public class Employee{
  int id;
  int permissionLevel;
  String username;
  String password;
  String firstName;
  String lastName;
  String email;
  String address;
  String city;
  String state;
  String hireDate;
  boolean isActive;



/**
Getters and Setters
                   **/

          //getters
  public int getId(){
    return id;
  }
  public int getPermissionLevel(){
    return permissionLevel;
  }
  public String getUsername(){
    return username;
  }
  public String getPassword(){
    return password;
  }
  public String getFirstName(){
    return firstName;
  }
  public String getLastName(){
    return lastName;
  }
  public String getEmail(){
    return email;
  }
  public String getAddress(){
    return address;
  }
  public String getState(){
    return state;
  }
  public String getHireDate(){
    return hireDate;
  }
  public String getIsActive(){
    return isActive;
  }
        //setters
  public void setId(int id){
    this.id = id;
  }
  public int setPermissionLevel(int permissionLevel){
    this.permissionLevel = permissionLevel;
  }
  public String setUsername(){
    this.username = username;
  }
  public String setPassword(){
    this.password = password;
  }
  public String setFirstName(){
    this.firstName = firstName ;
  }
  public String setLastName(){
    this.lastName = lastName;
  }
  public String setEmail(){
    this.email = email;
  }
  public String setAddress(){
    this.address = address;
  }
  public String setState(){
    this.state = state;
  }
  public String setHireDate(){
    this.hireDate = hireDate;
  }
  public String setIsActive(){
    this.isActive = isActive;
  }
}
