package com.cepheid.cloud.skel.repository;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.util.ItemPage;
import com.cepheid.cloud.skel.util.ItemSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ItemCriteriaRepository {
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public ItemCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Item> findAllWithFilters(ItemPage itemPage, ItemSearchCriteria itemSearchCriteria) {
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot = criteriaQuery.from(Item.class);
        Predicate predicate = getPredicate(itemSearchCriteria, itemRoot);
        criteriaQuery.where(predicate);
        setOrder(itemPage, criteriaQuery, itemRoot);
        TypedQuery<Item> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(itemPage.getPage() * itemPage.getSize());
        typedQuery.setMaxResults(itemPage.getSize());
        Pageable pageable = getPageable(itemPage);
        Long itemCount = getItemCount(predicate);
        return new PageImpl(typedQuery.getResultList(), pageable, itemCount);
    }

    private Long getItemCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Item> countRoot = countQuery.from(Item.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(ItemPage itemPage) {
        Sort sort = Sort.by(itemPage.getDirection(), itemPage.getSortBy());
        return PageRequest.of(itemPage.getPage(), itemPage.getSize(), sort);
    }

    private void setOrder(ItemPage itemPage, CriteriaQuery<Item> criteriaQuery, Root<Item> itemRoot) {
        if (itemPage.getDirection().isAscending()) {
            criteriaQuery.orderBy(criteriaBuilder.asc(itemRoot.get(itemPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(itemRoot.get(itemPage.getSortBy())));
        }
    }

    private Predicate getPredicate(ItemSearchCriteria itemSearchCriteria, Root<Item> itemRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(itemSearchCriteria.getId())) {
            predicates.add(criteriaBuilder.equal(itemRoot.get("id"), itemSearchCriteria.getId()));
        }
        if (Objects.nonNull(itemSearchCriteria.getName())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(itemRoot.get("name")),
                    "%" + itemSearchCriteria.getName().toLowerCase() + "%"));
        }
        if (Objects.nonNull(itemSearchCriteria.getId())) {
            predicates.add(criteriaBuilder.equal(itemRoot.get("status"),
                    itemSearchCriteria.getStatus()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


}
