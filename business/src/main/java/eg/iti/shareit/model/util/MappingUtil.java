package eg.iti.shareit.model.util;

import eg.iti.shareit.common.dto.GenericDto;
import eg.iti.shareit.common.entity.GenericEntity;
import eg.iti.shareit.model.dto.ActivityDto;
import eg.iti.shareit.model.entity.StatusEntity;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed_2 on 11/13/2015.
 */
@Stateless(mappedName = "mappingUtil")
public class MappingUtil {

    private Mapper mapper;

    public MappingUtil() {
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        List<String> configFileList = new ArrayList<String>();
        configFileList.add("dozer-mapping.xml");

        beanMapper.setMappingFiles(configFileList);

        mapper = (Mapper) beanMapper;
    }

    public <E extends GenericEntity, T extends GenericDto> T getDto(E entity, Class<T> dtoClass) {

        return mapper.map(entity, dtoClass);
    }

    public <E extends GenericEntity, T extends GenericDto> E getEntity(T dto, Class<E> entityClass) {

        return mapper.map(dto, entityClass);
    }

    public <E extends GenericEntity, T extends GenericDto> List<T> getDtoList(List<E> entityList, Class<T> dtoClass) {

        List<T> dtoList = new ArrayList<T>();

        for (E entity : entityList) {
            dtoList.add(getDto(entity, dtoClass));
        }

        return dtoList;
    }

}
