package com.buyern.inventory.Model;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Cacheable;
import javax.persistence.Id;
import java.security.Permission;
import java.util.List;
import java.util.Map;

@RedisHash("user")
@Data
public class UserPermissions {
    @Id
    private String id;
    /**
     * entityId and permission*/
    private Map<String,Permission> permissions;
}
