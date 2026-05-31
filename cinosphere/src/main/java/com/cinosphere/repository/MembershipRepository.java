package com.cinosphere.repository;

import com.cinosphere.model.MembershipModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<MembershipModel, Integer> {

    // SELECT * FROM membership WHERE user_id = ?
    Optional<MembershipModel> findByUserId(int userId);

    // UPDATE-style operations are handled via save()
}