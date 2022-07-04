package com.gucardev.postsharingbe.repository;

import com.gucardev.postsharingbe.model.UserSocketID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSocketIDRepository extends CrudRepository<UserSocketID,Long> {

}
