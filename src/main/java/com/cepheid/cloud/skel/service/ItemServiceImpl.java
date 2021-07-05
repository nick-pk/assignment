package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.exception.ResourceNotFoundException;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.ItemCriteriaRepository;
import com.cepheid.cloud.skel.repository.ItemRepository;
import com.cepheid.cloud.skel.util.ItemPage;
import com.cepheid.cloud.skel.util.ItemSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.NoContentException;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;
    private ItemCriteriaRepository itemCriteriaRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemCriteriaRepository itemCriteriaRepository) {
        this.itemRepository = itemRepository;
        this.itemCriteriaRepository = itemCriteriaRepository;
    }

    @Override
    public Page<Item> getItems(ItemSearchCriteria itemSearchCriteria, ItemPage itemPage) throws NoContentException {
        Page<Item> list = itemCriteriaRepository.findAllWithFilters(itemPage,itemSearchCriteria);
        if (!list.hasContent()) {
            throw new NoContentException("Data Unavailable");
        }
        return list;
    }


    @Override
    public Item getItem(Long id) throws ResourceNotFoundException {
        Optional<Item> optional = itemRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("Item with id: " + id + " Unavailable.");
        }
        return optional.get();
    }

    @Override
    public void deleteItem(Long id) throws ResourceNotFoundException {
        Optional<Item> optional = itemRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("Item with id: " + id + " Unavailable.");
        }
        itemRepository.deleteById(id);
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }
    @Override
    public Item updateItem(Item item) {
        Optional<Item> optionalPersistedItem = itemRepository.findById(item.getId());

        if (optionalPersistedItem.isPresent()) {
            item.getDescriptions().addAll(optionalPersistedItem.get().getDescriptions());
        }
        return itemRepository.save(item);
    }

}
