package com.buyern.inventory.Model;

import com.buyern.inventory.Objects.EntityPermission;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash("userAuth")
@Data
public class UserAuth {
    @Id
    private String userId;
    private List<EntityPermission> entities;

}
