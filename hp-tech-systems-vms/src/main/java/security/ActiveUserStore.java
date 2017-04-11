package security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;

public class ActiveUserStore {
 
    public List<String> users;
 
    public ActiveUserStore() {
        users = new ArrayList<String>();
    }
 
    public List<String> getUsers() {
    	users.add("1");
    	return users;
    }
}