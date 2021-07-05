package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.exception.ResourceNotFoundException;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.util.ItemPage;
import com.cepheid.cloud.skel.util.ItemSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.core.NoContentException;

public interface ItemService {
    public Page<Item> getItems(ItemSearchCriteria itemSearchCriteria, ItemPage itemPage) throws NoContentException;
    public Item getItem(Long id) throws ResourceNotFoundException;
    public void deleteItem(Long id) throws ResourceNotFoundException;
    public Item saveItem(Item item);
    public Item updateItem(Item item);

}
