package com.bank.bankingrest.repository;

import com.bank.bankingrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByLogin(String login);
    @Transactional
    @Modifying
    @Query(value = "UPDATE users set balance=:newbalance where id =:id",nativeQuery = true)
    void setAmountForUser(long newbalance,int id);

}
