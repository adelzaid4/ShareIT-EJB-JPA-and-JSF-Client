/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.service;

import eg.iti.shareit.model.dto.UserDto;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Singleton;

/**
 *
 * @author Dina Ashraf
 */
@Singleton
public class UserTrackingService {
     private static final Map<String, UserDto> usersMap = new HashMap<String, UserDto>();

     public void addUser(String key,UserDto userDto )
     {
         usersMap.put(key, userDto);
     }
}