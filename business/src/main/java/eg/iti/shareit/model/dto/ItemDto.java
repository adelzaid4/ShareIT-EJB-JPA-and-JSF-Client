/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dto;

import eg.iti.shareit.common.dto.GenericDto;
import eg.iti.shareit.model.entity.UserEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Yousef
 */
public class ItemDto implements java.io.Serializable, GenericDto {

    private BigDecimal id;
    private String name;
    private String description;
    private CategoryDto category;
    private int isAvailable;
    private Date publishDate;
    private int points;
    private String imageUrl;
    private UserDto userFrom;
    private String tags;

    public ItemDto() {
    }

    public ItemDto(BigDecimal id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ItemDto(BigDecimal id, String name, String description, CategoryDto category, int isAvailable, Date publishDate, int points) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.isAvailable = isAvailable;
        this.publishDate = publishDate;
        this.points = points;
    }

  

    public BigDecimal getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemDto other = (ItemDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public ItemDto(String name, String description, int isAvailable, Date publishDate, int points, String imageUrl, String tags, UserDto userDto) {
        this.name = name;
        this.description = description;
        this.isAvailable = isAvailable;
        this.publishDate = publishDate;
        this.points = points;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.userFrom = userDto;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserDto getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(UserDto userFrom) {
        this.userFrom = userFrom;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}
