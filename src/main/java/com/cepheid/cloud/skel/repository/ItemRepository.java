package com.cepheid.cloud.skel.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cepheid.cloud.skel.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
