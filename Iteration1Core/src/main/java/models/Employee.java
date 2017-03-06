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
  //boolean isActive;

  public Employee(){
  }

//toString()
@Override
public String toString(){
  String output = "";
  output += "Employee ID: " + this.id + "\n";
  output += "Employee Name: " + this.username + "\n";
  return output;
}

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
  public String getCity(){
    return city;
  }
  public String getState(){
    return state;
  }
  public String getHireDate(){
    return hireDate;
  }
  //public boolean getIsActive(){
    //return isActive;
  //}
        //setters
  public void setId(int id){
    this.id = id;
  }
  public void setPermissionLevel(int permissionLevel){
    this.permissionLevel = permissionLevel;
  }
  public void setUsername(String username){
    this.username = username;
  }
  public void setPassword(String password){
    this.password = password;
  }
  public void setFirstName(String firstName){
    this.firstName = firstName ;
  }
  public void setLastName(String lastName){
    this.lastName = lastName;
  }
  public void setEmail(String email){
    this.email = email;
  }
  public void setAddress(String address){
    this.address = address;
  }
  public void setCity(String city){
    this.city = city;
  }
  public void setState(String state){
    this.state = state;
  }
  public void setHireDate(String hireDate){
    this.hireDate = hireDate;
  }
  //public void setIsActive(){
    //this.isActive = isActive;
  //}
}
