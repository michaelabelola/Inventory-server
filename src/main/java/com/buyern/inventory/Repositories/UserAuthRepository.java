package com.buyern.inventory.Repositories;

import com.buyern.inventory.Model.UserAuth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends CrudRepository<UserAuth, String> {
}
