//package eg.iti.shareit.model.test.mapping;
//
//
//import eg.iti.shareit.common.Exception.ServiceException;
//
//import org.junit.Assert;
//import org.dozer.DozerBeanMapper;
//import org.dozer.Mapper;
//import org.junit.Before;
//import org.junit.Test;
//
//import javax.naming.NamingException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Mohamed_2 on 11/13/2015.
// */
//public class EntityMappingTest {
//
//
//    private Mapper mapper;
//
//    private MappingUtil mappingUtil;
//
//
//    @Before
//    public void init() throws NamingException {
//        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
//        List<String> configFileList = new ArrayList<String>();
//        configFileList.add("dozer-mapping.xml");
//
//        dozerBeanMapper.setMappingFiles(configFileList);
//
//        mapper = (Mapper) dozerBeanMapper;
//
//
//    }
//
//    @Test
//    public void UserEntityShouldMappedToUserDto() {
//        try {
//            UserService userService = new UserService();
//            UserDto userDto = userService.getUserByEmail("yousef");
//
//           
//
//        } catch (ServiceException e) {
//          
//           
//        }catch (Exception e) {
//            
//            
//        }
//        UserEntity userEntity = new UserEntity();
//
//        String name = "Hamada";
//
//        userEntity.setUsername(name);
//
//        UserDto UserDto = mapper.map(userEntity, UserDto.class);
//
//        Assert.assertNotNull(UserDto);
//
//        Assert.assertNotNull(UserDto.getUsername());
//        Assert.assertEquals(userEntity.getUsername(), UserDto.getUsername());
//
//    }
//
//    @Test
//    public void UserDtoShouldMappedToUserEntity() {
//
//        UserDto UserDto = new UserDto();
//
//        String name = "Hamada";
//
//        UserDto.setUsername(name);
//
//        UserEntity userEntity = mapper.map(UserDto, UserEntity.class);
//
//        Assert.assertNotNull(userEntity);
//
//        Assert.assertEquals(userEntity.getUsername(), UserDto.getUsername());
//
//    }
//
//
//}
