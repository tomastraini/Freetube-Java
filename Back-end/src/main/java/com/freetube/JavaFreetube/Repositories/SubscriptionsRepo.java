package com.freetube.JavaFreetube.Repositories;

import com.freetube.JavaFreetube.Models.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionsRepo extends JpaRepository<Subscriptions, Integer> {
}
